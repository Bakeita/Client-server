import socket
import threading
def create_server(port):
    server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server_socket.bind(('localhost', port))
    server_socket.listen()
    print(f"Server listening on port {port}")

    while True:
        conn, addr = server_socket.accept()
        print(f"Connected by {addr}")
        data = conn.recv(1024)
        print(f"Received {data.decode()} from {addr}")
        conn.sendall("Thanks for connecting".encode())
        conn.close()

def create_client(host, port):
    client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    client_socket.connect((host, port))
    client_socket.sendall("Hello, server!".encode())
    data = client_socket.recv(1024)
    print(f"Received {data.decode()} from {host}")
    client_socket.close()

if __name__ == '__main__':
    port = 5000
    host = 'localhost'

    # Start the server in a separate thread
    server_thread = threading.Thread(target=create_server, args=(port,))
    server_thread.start()

    # Connect to the server and send a message
    create_client(host, port)
