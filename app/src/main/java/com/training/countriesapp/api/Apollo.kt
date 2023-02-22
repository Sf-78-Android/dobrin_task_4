package com.training.countriesapp.api

import com.apollographql.apollo3.ApolloClient
import com.training.countriesapp.constants.Constants.APOLLO_API_LINK

object Apollo {

    val apolloClient = ApolloClient.Builder()
        .serverUrl(APOLLO_API_LINK)
        .build()

}
