package sync

import upickle.default.{Reader, Writer}

import java.io.{DataInputStream, DataOutputStream}

object Shared {
    def send[T: Writer](out: DataOutputStream, message: T): Unit = {
        val bytes = upickle.default.writeBinary(message)
        out.writeInt(bytes.length)
        out.write(bytes)
        out.flush()
    }

    def receive[T: Reader](in: DataInputStream): Unit = {
        val buf = new Array[Byte](in.readInt())
        in.readFully(buf)
        upickle.default.readBinary[T](buf)
    }
}
