import java.text.SimpleDateFormat as SDF
import org.xml.sax.SAXParseException
import groovy.servlet.*

def url = "https://groups.google.com/forum/feed/caelyf/topics/rss_v2_0.xml".toURL();

try {
    def slurper = new XmlSlurper()
    def tx = url.getText(requestProperties:['User-agent':'Firefox/2.0.0.4']);  
    // may need this later: 'UTF-8', connectTimeout: 500, readTimeout:2000)

    def result = slurper.parseText(tx)
    def sdf = new SDF('EEE, dd MMM yyyy HH:mm:ss z', Locale.US)

        result.channel.item.each { item ->
                println "\n["+item.title.text()+"]" 
                def who = item.author.text()
                who =  who.replace('\n',' ').trim()
                println "posted by [${who}] link:[${item.link.text()}]"
                println item.description.text().replace("<br>"," ")
        } // end of each
    
} // end of try

catch (SAXParseException spe) 
{
    def msg = spe.message;
    html.div {
        span "A problem occurred while parsing the Google Group RSS feed, please go directly to the"
        a href: "http://groups.google.com/group/caelyf", "Caelyf Google Group"
        span " instead. Or try again later."
        span "  Failure:${msg}"
    }
} 

