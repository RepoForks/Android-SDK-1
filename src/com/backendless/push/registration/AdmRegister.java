/*
 * ********************************************************************************************************************
 *  <p/>
 *  BACKENDLESS.COM CONFIDENTIAL
 *  <p/>
 *  ********************************************************************************************************************
 *  <p/>
 *  Copyright 2012 BACKENDLESS.COM. All Rights Reserved.
 *  <p/>
 *  NOTICE: All information contained herein is, and remains the property of Backendless.com and its suppliers,
 *  if any. The intellectual and technical concepts contained herein are proprietary to Backendless.com and its
 *  suppliers and may be covered by U.S. and Foreign Patents, patents in process, and are protected by trade secret
 *  or copyright law. Dissemination of this information or reproduction of this material is strictly forbidden
 *  unless prior written permission is obtained from Backendless.com.
 *  <p/>
 *  ********************************************************************************************************************
 */

package com.backendless.push.registration;

import android.content.Context;
import android.content.Intent;
//import com.amazon.device.messaging.ADM;

import java.util.Date;

class AdmRegister implements IRegistrar
{
  @Override
  public void register( Context context, String senderId, Date expiration, IDeviceRegistrationCallback callback )
  {
    //ADM adm = new ADM( context );
    //context.inte
  }

  @Override
  public void unregister( Context context, IDeviceRegistrationCallback callback )
  {

  }

  @Override
  public void registrationCompleted( String senderId, String deviceToken, Long registrationExpiration,
                                     String callbackId )
  {

  }

  @Override
  public void registrationFailed( String error, String callbackId )
  {

  }

  @Override
  public void unregistrationCompleted( String callbackId )
  {

  }

  @Override
  public void unregistrationFailed( String error, String callbackId )
  {

  }

  @Override
  public boolean isRegistered( Context context )
  {
    return false;
  }
}