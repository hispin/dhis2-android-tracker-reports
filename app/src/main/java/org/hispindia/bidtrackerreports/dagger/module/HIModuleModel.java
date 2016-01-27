package org.hispindia.bidtrackerreports.dagger.module;

import dagger.Module;

/**
 * Created by nhancao on 1/18/16.
 */
@Module
public class HIModuleModel {
//    private static OkHttpClient getUnsafeOkHttpClient() {
//        try {
//            // Create a trust manager that does not validate certificate chains
//            final TrustManager[] trustAllCerts = new TrustManager[]{
//                    new X509TrustManager() {
//                        @Override
//                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
//                        }
//
//                        @Override
//                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
//                        }
//
//                        @Override
//                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
//                            return null;
//                        }
//                    }
//            };
//
//            // Install the all-trusting trust manager
//            final SSLContext sslContext = SSLContext.getInstance("SSL");
//            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
//
//            // Create an ssl socket factory with our all-trusting manager
//            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
//
//            OkHttpClient okHttpClient = new OkHttpClient();
//            okHttpClient.newBuilder().sslSocketFactory(sslSocketFactory);
//            okHttpClient.interceptors().add(new Interceptor() {
//                @Override
//                public Response intercept(Chain chain) {
//                    try {
//                        Request request = chain.request();
//                        Buffer buffer = new Buffer();
//                        if (request.body() != null)
//                            request.body().writeTo(buffer);
//                        if (buffer.size() == 0) buffer.writeUtf8("nothing in body");
//                        Log.e("Retrofit", String.format("Method: %s - Request to %s with->\nBODY: %s", request.method(), request.url(), buffer.readUtf8()));
//                        long t1 = System.nanoTime();
//                        Response response = chain.proceed(request);
//                        long t2 = System.nanoTime();
//                        String msg = response.body().string();
//                        Log.e("Retrofit", String.format("Response from %s in %.1fms%n",
//                                response.request().url(), (t2 - t1) / 1e6d));
//                        Log.e("Retrofit", "response: " + msg);
//                        return response
//                                .newBuilder()
//                                .body(ResponseBody.create(response.body().contentType(), msg))
//                                .build();
//
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                        return null;
//                    }
//                }
//            });
//            if (HIApiEnvConfig.isNeedVerifyHost()) {
//                okHttpClient.newBuilder().hostnameVerifier((hostname, session) -> hostname.contains(HIApiEnvConfig.getHostUrl()));
//
//            }
//            return okHttpClient;
//        } catch (Exception e) {
//            Log.e("Retrofit", "getUnsafeOkHttpClient: " + e.toString());
////            throw new RuntimeException(e);
//            return new OkHttpClient();
//        }
//    }
//
//
//    private Retrofit getRestAdapter(String BASE_URL) {
//        Retrofit restAdapter = new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
//                        .setExclusionStrategies(new ExclusionStrategy() {
//                            @Override
//                            public boolean shouldSkipField(FieldAttributes f) {
//                                return f.getDeclaringClass().equals(RealmObject.class);
//                            }
//
//                            @Override
//                            public boolean shouldSkipClass(Class<?> clazz) {
//                                return false;
//                            }
//                        })
//                        .create()))
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                .client(getUnsafeOkHttpClient())
//                .build();
//        return restAdapter;
//    }

}
