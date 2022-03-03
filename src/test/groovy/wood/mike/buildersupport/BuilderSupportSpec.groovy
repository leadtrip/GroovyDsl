package wood.mike.buildersupport

import groovy.json.JsonOutput
import groovy.xml.MarkupBuilder
import groovy.xml.XmlSlurper
import groovy.xml.XmlUtil
import spock.lang.Specification
import wood.mike.buildersupport.customer.CustomersBuilder
import wood.mike.buildersupport.workout.WorkoutBuilder
import wood.mike.buildersupport.workout.WorkoutFactoryBuilder

class BuilderSupportSpec extends Specification{

    /**
     * Creating XML with groovy's MarkupBuilder
     */
    void "test xml builder"() {
        given:
            def writer = new StringWriter()
            def xml = new MarkupBuilder( writer )
        when:
            xml.cyclists{
                cyclist {
                    personal {
                        name("Chris Froome")
                        dob("20-05-1985")
                        weight(68)
                    }
                    results {
                        year(value: 2013) {
                            event(name: 'Critérium du Dauphiné') {
                                position(1)
                                stageWins(3)
                            }
                            event(name: 'Tour de France') {
                                postion(1)
                                stageWins(1)
                            }
                        }
                        year(value: 2014) {
                            event(name: 'Tour de Romandie') {
                                position(1)
                                stageWins(1)
                            }
                        }
                    }
                }
            }
        then:
            def cyclist = new XmlSlurper().parseText( writer.toString() )
            println XmlUtil.serialize( cyclist )
    }

    /**
     * Testing LogBuilder that extends BuilderSupport, this implementation simply logs method calls to provide
     * feedback on flow
     */
    void "test log builder"() {
        given:
            def builder = new LogBuilder()

            def customers = builder.customers {
                customer{
                    id(1001)
                    name(firstName:"Fred",surname:"Flintstone")
                    address("billing", street:"1 Rock Road",city:"Bedrock")
                    address("shipping", street:"1 Rock Road",city:"Bedrock")
                }
            }
        expect:
            1
    }

    /**
     * Testing the Grails domain like CustomersBuilder that extends BuilderSupport
     */
    void "test customer builder"() {
        given:
            def builder = new CustomersBuilder()

            def customers = builder.customers {
                def fred = customer(firstName:"Fred",lastName:"Flintstone") {
                    invoice {
                        salesOrder(sku:"productid01", amount:1, price:1.00)
                        salesOrder(sku:"productid02", amount:2, price:1.00)
                        salesOrder(sku:"productid03", amount:3, price:1.00)
                    }
                }
                def invoice2 = invoice(fred)

                salesOrder(invoice2, sku:"productid04", amount:1, price:1.00)
                salesOrder(invoice2, sku:"productid05", amount:1, price:1.00)
                salesOrder(invoice2, sku:"productid06", amount:1, price:1.00)
            }

        expect:
            1
            // not actually using Grails here so below won't work but get the point
            /*CustomerWithInvoices.count() == 1
            Invoice.count() == 2
            SalesOrder.count() == 6*/
    }

    /**
     * Testing the WorkoutBuilder that extends BuilderSupport
     */
    void "test workout builder"() {
        given:
            def builder = new WorkoutBuilder()

            def workout = builder.workout{
                warmup ( activity: 'cycling', duration: 10 )
                exercise( name: 'Squats' ) {
                    exerciseSet( setNumber: 1, reps: 10, rpe: 3 )
                    exerciseSet( setNumber: 2, reps: 8, rpe: 5 )
                    exerciseSet( setNumber: 3, reps: 6, rpe: 8 )
                }
                exercise( name: 'Leg extension' ) {
                    exerciseSet( setNumber: 1, reps: 10, rpe: 3 )
                    exerciseSet( setNumber: 2, reps: 8, rpe: 5 )
                    exerciseSet( setNumber: 3, reps: 6, rpe: 8 )
                }
            }
        expect:
            println JsonOutput.prettyPrint( JsonOutput.toJson(workout) )
    }

    /**
     * Same result as above but using factory builder
     */
    void "test workout factory builder"() {
        given:
            def builder = new WorkoutFactoryBuilder()

        def workout = builder.workout{
            warmup ( activity: 'cycling', duration: 10 )
            exercise( name: 'Squats' ) {
                exerciseSet( setNumber: 1, reps: 10, rpe: 3 )
                exerciseSet( setNumber: 2, reps: 8, rpe: 5 )
                exerciseSet( setNumber: 3, reps: 6, rpe: 8 )
            }
            exercise( name: 'Leg extension' ) {
                exerciseSet( setNumber: 1, reps: 10, rpe: 3 )
                exerciseSet( setNumber: 2, reps: 8, rpe: 5 )
                exerciseSet( setNumber: 3, reps: 6, rpe: 8 )
            }
        }
        expect:
            println JsonOutput.prettyPrint( JsonOutput.toJson(workout) )
    }
}
