import mill._, scalalib._

trait SyncModule extends ScalaModule {
    def scalaVersion = "2.13.2"

    override def ivyDeps = Agg(
        ivy"com.lihaoyi::upickle:1.2.0",
        ivy"com.lihaoyi::os-lib:0.7.1"
    )
}

object shared extends SyncModule

object sync extends SyncModule {
    override def moduleDeps = Seq(shared)
}

object agent extends SyncModule {
    override def moduleDeps = Seq(shared)
}
