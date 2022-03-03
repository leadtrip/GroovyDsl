package wood.mike.buildersupport.customer

/**
 * Mimicking a Grails domain
 */
class CustomerWithInvoices {
    String firstName
    String lastName
    List<Invoice> invoices = []

    void addToInvoices( Invoice invoice ) {
        invoices.add( invoice )
    }

    void save() {
        println 'saving customer with invoices'
    }
}
