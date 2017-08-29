import socket
import sys
import function

HOST = ""  # Symbolic name, meaning all available interfaces
PORT = 8888  # Arbitrary non-privileged port

ROBOT_SENSORS = "Master Robot"
ROBOT_WITHOUT_SENSORS = "Slave Robot"


def main():
    # Address Family : AF_INET (this is IP version 4 or IPv4)
    # Type : SOCK_STREAM (this means connection oriented TCP protocol)

    function.print_debug("[INFO] Starting the Ploudy")
    ploudy_server = function.start_server(HOST, PORT)

    l_data_base = []

    while 1:

        function.print_debug("[INFO] Waiting a new client...")

        # wait to accept a connection - blocking call
        # addr0 is the IP and and addr1 is the ports
        client_connection, client_addr = ploudy_server.accept()
        function.print_debug("\n[INFO] Connected with " + client_addr[0] + " : " + str(client_addr[1]))

        robot = client_connection.recv(1024)

        function.print_debug("\n\tReceived: \"" + robot + "\"")

        if ROBOT_SENSORS in robot:
            if len(l_data_base) > 0:
                function.print_debug("[ERRO] Client was desconnected!")
            else:
                try:
                    function.sensors_robot_procedure(client_connection, l_data_base)
                except:
                    function.print_debug("[ERRO] Client was desconnected by a error!")
        elif ROBOT_WITHOUT_SENSORS in robot:
            try:
                function.slave_robot_procedure(client_connection, l_data_base)
            except:
                function.print_debug("[ERRO] Client was desconnected by a error!")

        client_connection.close()
    ploudy_server.close()

main()