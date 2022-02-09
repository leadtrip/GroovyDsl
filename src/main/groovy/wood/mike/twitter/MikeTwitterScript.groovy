package wood.mike.twitter

import twitter4j.*
import static twitter4j.TwitterFactory.*

abstract class MikeTwitterScript extends Script{
    void trends(woeid) {
        singleton.trends().getPlaceTrends(woeid.toInteger()).each {trend ->
            println "Getting trends for: ${trend.location.name} as of ${trend.asOf}"
            trend.trends.each { aTrend->
                println "\t * ${aTrend.name}"
            }
        }
    }
}
