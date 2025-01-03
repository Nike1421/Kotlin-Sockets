import java.io.OutputStream
import java.net.ServerSocket
import java.net.Socket
import java.nio.charset.Charset
import java.util.*
import kotlin.concurrent.thread


fun main() {
    val server = ServerSocket(9999)
    println("Server is running on port ${server.localPort}")

    while (true) {
        val client = server.accept()
        println("Client connected: ${client.inetAddress.hostAddress}")

        // Run client in its own thread.
        thread { ClientHandler(client).run() }
    }
}

class ClientHandler(client: Socket) {
    private val client: Socket = client
    private val reader: Scanner = Scanner(client.getInputStream())
    private val writer: OutputStream = client.getOutputStream()
    private val calculator: Calculator = Calculator()
    private var running: Boolean = false

    fun run() {
        running = true
        write("Connected to Server!\n")
        while (running) {
            try {
                write("Enter Operation in (ADD, SUB, MULTI, DIV) or 'EXIT': ")
                val text = reader.nextLine()
                if (text == "EXIT"){
                    shutdown()
                    break
                }
                write("Enter First Number: ")
                val firstNumber = reader.nextLine()
                write("Enter Second Number: ")
                val secondNumber = reader.nextLine()
                val result = calculator.calculate(firstNumber.toInt(), secondNumber.toInt(), text)
                write(result)
            } catch (ex: Exception) {
                // TODO: Implement exception handling
                shutdown()
            } finally {

            }
        }
    }

    private fun write(message: String) {
        writer.write((message + '\n').toByteArray(Charset.defaultCharset()))
    }

    private fun shutdown() {
        running = false
        client.close()
        println("${client.inetAddress.hostAddress} closed the connection")
    }
}

