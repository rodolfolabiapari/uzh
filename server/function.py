import socket
import sys


max_rotation_motor = 250
DEBUG = True
Ki = Kd = 0
Kp = 10
perfect_position = 0

def print_debug(s):
    global DEBUG

    if DEBUG:
        print s


def start_server(HOST, PORT):
    try:
        # create an AF_INET, STREAM socket (TCP)
        s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    except socket.error, msg:
        print "[INFO] Failed to create socket. Error code: " + str(msg[0]) + " , Error message : " + msg[1]
        sys.exit()

    print "[INFO] Socket created"

    # Bind socket to local host and port
    try:
        s.bind((HOST, PORT))
    except socket.error as msg:
        print "[INFO] Bind failed. Error Code : " + str(msg[0]) + " Message " + msg[1]
        sys.exit()

    print "[INFO] Socket bind complete"

    # Start listening on socket for ten clients
    s.listen(10)
    print "[INFO] Socket now listening"

    return s


# <led_esq> <led_dir>  <acel_x> <acel_y> <acel_z>  <giro_x> <giro_y> <giro_z>"
def verify_parameters (client):
    # now keep talking with the client
    package = client.recv(1024)

    print_debug("Received: \"" + package + "\"")

    list_package = package.split()

    if len(list_package) == 6:
        time = float(list_package[0])

        led_esq = float(list_package[1])
        led_dir = float(list_package[2])

        acel_x = float(list_package[3])
        acel_y = float(list_package[4])
        #acel_z = float(list_package[4])

        degree = float(list_package[5])
        #giro_y = float(list_package[6])

    else:
        print_debug("[ERRO] Erro na leitura do sensor")
        return 0, 0, 0, 0, 0, 0

    return time, led_esq, led_dir,  acel_x, acel_y,  degree


def process_sensors(light_left, light_righ):
    # Calcule the media

    """
    if light_left == 1:
        media_sensors = 0

    elif light_righ == 1:
        media_sensors = 2

    else:
        media_sensors = 1
    """

    if light_left == 1:
        media_sensors = -10

    elif light_righ == 1:
        media_sensors = 10

    else:
        media_sensors = 0

    # Calcule the sum todo need?
    sum_sensors = media_sensors

    return media_sensors, sum_sensors


def calcule_proportion(media_sensors, sum_sensors, integral, last_proportion):
    #position = int(media_sensors / sum_sensors)
    position = media_sensors
    proportion = position - perfect_position
    integral = integral + proportion
    derivate = proportion - last_proportion
    last_proportion = proportion

    error_value = int(proportion * Kp + integral * Ki + derivate * Kd)

    return error_value


def calcule_spin(error_value):
    """
    if error_value < -5:
        error_value = -5
    elif error_value > 5:
        error_value = 5
    """

    if error_value < 0:
        rotation_righ_motor = max_rotation_motor
        rotation_left_motor = max_rotation_motor - error_value

    elif error_value > 0:
        rotation_left_motor = max_rotation_motor
        rotation_righ_motor = max_rotation_motor - error_value

    else:
        rotation_left_motor = max_rotation_motor
        rotation_righ_motor = max_rotation_motor

    return rotation_left_motor, rotation_righ_motor


def send_new_rotations(client, rotation_left_motor, rotation_righ_motor):
    new_package = str(rotation_left_motor) + " " + str(rotation_righ_motor)

    client.sendall(new_package)



def sensors_robot_procedure(client_connection, l_data_base):
    print_debug("[INFO] <time>  <light_left> <light_righ>  <acel_x> <acel_y>  <degrees>")

    last_proportion = 0

    # now keep talking with the client
    while 1:
        # Verify the parameters
        time, light_left, light_righ, acel_x, acel_y, degree = verify_parameters(client_connection)

        if time == 0:
            return

        # Process
        media_sensors, sum_sensors = process_sensors(light_left, light_righ)

        error_value = calcule_proportion(media_sensors, sum_sensors, integral=0, last_proportion=last_proportion)

        rotation_left_motor, rotation_righ_motor = calcule_spin(error_value)

        l_data_base.append([ time, rotation_left_motor, rotation_righ_motor, acel_x, acel_y, degree ])

        send_new_rotations(client_connection, rotation_left_motor, rotation_righ_motor)



# =======================================================================================


def send_new_rotations_slave(client, list_rotations):

    client.sendall(str(len(list_rotations)) + "\n")

    for rotation_i in list_rotations:
        client.sendall(str(rotation_i[0]) + " " + str(rotation_i[1]) + " " + str(rotation_i[2]) + "\n")


def slave_robot_procedure(client_connection, l_data_base):
    print_debug("[INFO] <time>  <left_motor> <right_motor>")


    print_debug("[INFO] Sending the Map")
    send_new_rotations_slave(client_connection, l_data_base)

    print_debug("[INFO] Sending Done")

    print_debug("[INFO] Closing Connection")