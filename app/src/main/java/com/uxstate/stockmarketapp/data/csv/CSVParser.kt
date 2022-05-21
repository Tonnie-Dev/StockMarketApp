package com.uxstate.stockmarketapp.data.csv

import com.uxstate.stockmarketapp.domain.model.CompanyListing
import java.io.InputStream
/*A CSV (comma-separated values) file is a text file that has a specific
format which allows data to be saved in a table structured format.*/



/*We should always depend on abstractions and not concretions
* Therefore we create an interface which we can later depend on */

interface CSVParser<T> {

    //returns the parsed list from the CVS file
    suspend fun parse (stream: InputStream): List<CompanyListing>
}