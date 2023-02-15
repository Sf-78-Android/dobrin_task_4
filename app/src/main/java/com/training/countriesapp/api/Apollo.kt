package com.training.countriesapp

import com.apollographql.apollo3.ApolloClient


val apolloClient = ApolloClient.Builder()
    .serverUrl("https://countries.trevorblades.com/graphql")
    .build()