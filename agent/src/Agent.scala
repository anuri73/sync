package sync

import java.io.DataInputStream
import java.io.DataOutputStream

object Agent {
    def main(args: Array[String]): Unit = {
        val input = new DataInputStream(System.in)
        val output = new DataOutputStream(System.out)
        while (true) try {
            val rpc: Rpc = Shared.receive[Rpc](input)
            rpc match {
                case Rpc.StatPath(path) =>
                    Shared.send(output, Rpc.StatInfo(path, Shared.hashPath(os.pwd / path)))

                case Rpc.WriteOver(bytes, path) =>
                    os.remove.all(os.pwd / path)
                    os.write.over(os.pwd / path, bytes, createFolders = true)
            }
        } catch {
            case e: java.io.EOFException => System.exit(0)
        }
    }
}
