package com.finbourne.configuration.extensions;

import com.finbourne.configuration.ApiException;
import com.finbourne.configuration.api.ConfigurationSetsApi;
import com.finbourne.configuration.model.ResourceListOfConfigurationSetSummary;
import com.finbourne.configuration.extensions.auth.FinbourneTokenException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.assertThat;

 public class ApiFactoryBuilderTests {

     @Rule
     public ExpectedException thrown = ExpectedException.none();

     @Test
     public void build_WithExistingConfigurationFile_ShouldReturnFactory() throws ApiException, ApiConfigurationException, FinbourneTokenException {
         ApiFactory apiFactory = ApiFactoryBuilder.build(CredentialsSource.credentialsFile);
         assertThat(apiFactory, is(notNullValue()));
         assertThatFactoryBuiltApiCanMakeApiCalls(apiFactory);
     }

     private static void assertThatFactoryBuiltApiCanMakeApiCalls(ApiFactory apiFactory) throws ApiException {
         ConfigurationSetsApi configurationSetsApi = apiFactory.build(ConfigurationSetsApi.class);
         ResourceListOfConfigurationSetSummary listOfConfigurationSetSummary = configurationSetsApi.listConfigurationSets(null, null);
         assertThat("Configuration sets API created by factory should return list"
                 , listOfConfigurationSetSummary, is(notNullValue()));
         assertThat("list contents types returned by the configuration sets API should not be empty",
                 listOfConfigurationSetSummary.getValues(), not(empty()));
     }

 }
