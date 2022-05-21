package com.uxstate.stockmarketapp.data.csv

import com.opencsv.CSVReader
import com.uxstate.stockmarketapp.domain.model.CompanyListing
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import javax.inject.Inject
import javax.inject.Singleton


/*We need an inject annotation in front of the constructor
* so that dagger hilt knows how to create a CompanyListing
* object an provide it for other dependencies
*
* We use singleton annotation to force one single instance
* of this class for the entire app*/
@Singleton
class CompanyListingParser @Inject constructor() : CSVParser<CompanyListing> {
    override suspend fun parse(stream: InputStream): List<CompanyListing> {

        /*we use the open CVS Library and parse the CVS input stream
        * into a list of CompanyListing*/

        /*create a csv reader which takes an InputStreamReader which
        in return takes a stream*/
        val csvReader = CSVReader(InputStreamReader(stream))


        //return CSV's reader output vio IO Dispatcher

        return withContext(IO) {

//csv returns a list of String arrays (List <String []> readAll() )
            csvReader.readAll() //ignore this error as this is being done on IO Thread
                    .drop(1)// drop title
                    .mapNotNull {

                            line ->

                        val symbol = line.getOrNull(0)
                        val name = line.getOrNull(1)
                        val exchange = line.getOrNull(2)

                        //construct Company Listing Object

                        CompanyListing(
                            symbol = symbol ?: return@mapNotNull null,
                            name = name ?: return@mapNotNull null,
                            exchange = exchange ?: return@mapNotNull null
                        )

                        //also returns the original object type
                    }.apply{

                        csvReader.close()
                    }
        }

    }
}