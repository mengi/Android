package com.android.trader.connection;

import com.android.trader.data.model.PortfoyResult;
import com.android.trader.data.model.TraderResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by menginar on 11.01.2018.
 */

public interface TraderApiService {
    //MsgType=A&CustomerNo=0&Username=proje&Password=proje&AccountID=0&ExchangeID=4&OutputType=2
    //MsgType=AN&CustomerNo=0&Username=proje&Password=proje&AccountID=804587&ExchangeID=4&OutputType=2

    // Login
    @GET("Integration.aspx")
    Call<TraderResult> getPortfoyLogin(@Query("MsgType") String msgType,
                                @Query("CustomerNo") Integer customerNo,
                                @Query("Username") String username,
                                @Query("Password") String password,
                                @Query("AccountID") Integer accountID,
                                @Query("ExchangeID") Integer exchangeID,
                                @Query("OutputType") Integer outputType);

    // Portföy
    @GET("Integration.aspx")
    Call<PortfoyResult> getPortfoyDetail(@Query("MsgType") String msgType,
                                   @Query("CustomerNo") Integer customerNo,
                                   @Query("Username") String username,
                                   @Query("Password") String password,
                                   @Query("AccountID") Integer accountID,
                                   @Query("ExchangeID") Integer exchangeID,
                                   @Query("OutputType") Integer outputType);
}
