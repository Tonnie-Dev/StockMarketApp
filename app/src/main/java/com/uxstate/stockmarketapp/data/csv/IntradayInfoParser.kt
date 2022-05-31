package com.uxstate.stockmarketapp.data.csv

import com.opencsv.CSVReader
import com.uxstate.stockmarketapp.data.mapper.toIntradayInfo
import com.uxstate.stockmarketapp.data.remote.dto.IntradayInfoDTO
import com.uxstate.stockmarketapp.domain.model.IntradayInfo
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.InputStream
import java.io.InputStreamReader
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton


/*We need an inject annotation in front of the constructor
* so that dagger hilt knows how to create a IntradayInfoParser
* object and provide it for other dependencies
*
* We use singleton annotation to force one single instance
* of this class for the entire app*/

//Reads CSV file stream and returns IntradayInfoDTO
@Singleton
class IntradayInfoParser @Inject constructor() : CSVParser<IntradayInfo> {
    override suspend fun parse(stream: InputStream): List<IntradayInfo> {

        /*we use the open CVS Library and parse the CVS input stream
        * into a list of CompanyListing*/

        /*create a csv reader which takes an InputStreamReader which
        in return takes a stream*/
        val csvReader = CSVReader(InputStreamReader(stream))



        //return CSV's reader output vio IO Dispatcher

        return withContext(IO) {

            //csv returns a list of String arrays (List <String []> readAll() )
            csvReader.readAll() //ignore this error as this is being done on IO Thread
                    .drop(1) // drop title
                    .mapNotNull {

                        line ->

                        //pass column index, return null if such a field doesn't exist
                        val timestamp = line.getOrNull(0) ?: return@mapNotNull null
                        val close = line.getOrNull(4) ?: return@mapNotNull null


                        /*     we need to work with local date, instead of constructing
                              IntradayInfo object directly,  we construct
                              IntradayInfoDTO object and then map to to IntradayInfo*/

                        val dto = IntradayInfoDTO(
                                timeStamp = timestamp,
                                close = close.toDouble()
                        )

                        //explicit return for map fxn

                        dto.toIntradayInfo()


                    }.filter {
                      //  it.date.dayOfWeek == DayOfWeek.SATURDAY
                    it.date.dayOfWeek!= DayOfWeek.SUNDAY }


                    .filter {
                        /*  some timestamps overlaps over 2 days period but we
                          are interested in yesterday*/

                        Timber.i("The Day of month -4 is: ${LocalDate.now().minusDays(4).dayOfMonth}")
                        Timber.i("The Day of month -3 is: ${LocalDate.now().minusDays(3).dayOfMonth}")
                        Timber.i("The Day of month -2 is: ${LocalDate.now().minusDays(2).dayOfMonth}")
                        Timber.i("The Day of month -1 is: ${LocalDate.now().minusDays(1).dayOfMonth}")
                        it.date.dayOfMonth == LocalDate.now()
                                .minusDays(1).dayOfMonth


                    }
                    //sort by hour
                    .sortedBy { it.date.hour }

                    //also returns the original object type
                    .apply {

                        //ignore this as we are using IO Dispatcher
                        csvReader.close()
                    }
        }

    }
}