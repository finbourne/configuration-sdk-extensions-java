package com.finbourne.configuration.extensions;

import com.finbourne.configuration.ApiClient;
import com.finbourne.configuration.api.ConfigurationSetsApi;
import com.finbourne.configuration.model.ActionId;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class ApiFactoryTest {

    private ApiFactory apiFactory;
    private ApiClient apiClient;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp(){
        apiClient = mock(ApiClient.class);
        apiFactory = new ApiFactory(apiClient);
    }

    // General Cases

     @Test
     public void build_ForConfigurationSetsApi_ReturnConfigurationSetsApi(){
         ConfigurationSetsApi configurationSetsApi = apiFactory.build(ConfigurationSetsApi.class);
         assertThat(configurationSetsApi, instanceOf(ConfigurationSetsApi.class));
     }

     @Test
     public void build_ForAnyApi_SetsTheApiFactoryClientAndNotTheDefault(){
         ConfigurationSetsApi configurationSetsApi = apiFactory.build(ConfigurationSetsApi.class);
         assertThat(configurationSetsApi.getApiClient(), equalTo(apiClient));
     }

     // Singleton Check Cases

     @Test
     public void build_ForSameApiBuiltAgainWithSameFactory_ReturnTheSameSingletonInstanceOfApi(){
         ConfigurationSetsApi configurationSetsApi = apiFactory.build(ConfigurationSetsApi.class);
         ConfigurationSetsApi configurationSetsApiSecond = apiFactory.build(ConfigurationSetsApi.class);
         assertThat(configurationSetsApi, sameInstance(configurationSetsApiSecond));
     }

     @Test
     public void build_ForSameApiBuiltWithDifferentFactories_ReturnAUniqueInstanceOfApi(){
         ConfigurationSetsApi configurationSetsApi = apiFactory.build(ConfigurationSetsApi.class);
         ConfigurationSetsApi configurationSetsApiSecond = new ApiFactory(mock(ApiClient.class)).build(ConfigurationSetsApi.class);
         assertThat(configurationSetsApi, not(sameInstance(configurationSetsApiSecond)));
     }

     // Error Cases

     @Test
     public void build_ForNonApiPackageClass_ShouldThrowException(){
         thrown.expect(UnsupportedOperationException.class);
         thrown.expectMessage("com.finbourne.configuration.model.ActionId class is not a supported API class. " +
                 "Supported API classes live in the " + ApiFactory.API_PACKAGE + " package.");
         apiFactory.build(ActionId.class);
     }



}
