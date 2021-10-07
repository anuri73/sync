import mill._
import mill.define.Sources
import mill.scalalib._

trait SyncModule extends ScalaModule {
    def scalaVersion = "2.13.2"

    override def ivyDeps = Agg(
        ivy"com.lihaoyi::upickle:1.2.0",
        ivy"com.lihaoyi::os-lib:0.7.1",
        ivy"com.lihaoyi::os-lib-watch:0.7.1",
        ivy"com.lihaoyi::castor:0.1.7"
    )
}

object shared extends SyncModule

object sync extends SyncModule {
    override def moduleDeps = Seq(shared)

    override def resources: Sources = T.sources {
        os.copy(agent.assembly().path, T.dest / "agent.jar")
        super.resources() ++ Seq(PathRef(T.dest))
    }
}

object agent extends SyncModule {
    override def moduleDeps = Seq(shared)
}
