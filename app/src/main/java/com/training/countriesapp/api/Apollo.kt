package com.training.countriesapp.api

import com.apollographql.apollo3.ApolloClient


val apolloClient = ApolloClient.Builder()
    .serverUrl("https://countries.trevorblades.com/graphql")
    .build()