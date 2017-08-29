import SimpleHTTPServer
import SocketServer

PORT = 8000

Handler = SimpleHTTPServer.SimpleHTTPRequestHandler

httpd = SocketServer.TCPServer(("", PORT), Handler)

print httpd.address_family

print httpd.server_address

print "serving at port", PORT
# httpd.serve_forever()