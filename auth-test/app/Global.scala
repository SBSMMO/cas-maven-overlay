import play.api._
import play.api.Play.current
import org.pac4j.core.client._
import org.pac4j.play._
import org.pac4j.cas.client._

object Global extends GlobalSettings {

  override def onStart(app: Application) {
  
    val baseUrl = Play.application.configuration.getString("baseUrl").get
    val casUrl = Play.application.configuration.getString("casUrl").get
    
	val casClient = new CasClient()
	
	casClient.setCasLoginUrl(casUrl)
	
	val clients = new Clients(baseUrl + "/callback", casClient)
	
    Config.setClients(clients)
	
    Logger.info("Application has started")
  }
  
}