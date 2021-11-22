package com.neppplus.colosseum_20211117.utils

import android.content.Context
import android.provider.ContactsContract
import android.util.Log
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import org.json.JSONObject
import java.io.IOException

class ServerUtil {

    interface JsonResponseHandler {
        fun onResponse(jsonObject: JSONObject)
    }

    companion object {

//        여기에 적는 변수 / 함수는 => JAVA의 static에 대응됨.
//        클래스이름.기능() 으로 활용 가능.

//        모든 함수 (기능) 가 공유할 서버 컴퓨터 주소.

        val HOST_URL = "http://54.180.52.26"

//        로그인 함수 - POST

        fun postRequestLogin(email: String, pw: String, handler: JsonResponseHandler?) {

//            1. 어디로 가야? URL

            val urlString = "${HOST_URL}/user"

//            2. 어떤 데이터를 들고가는가? (파라미터)

            val formData = FormBody.Builder()
                .add("email", email)
                .add("password", pw)
                .build()

//            3. 어떤 메소드 + 1/2 데이터 결합 => 어떤 요청인지 완성

            val request = Request.Builder()
                .url(urlString)
                .post(formData)
                .build()

//            4. 완성된 Request를 실제로 호출 => 클라이언트 역할.

            val client = OkHttpClient()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {

//                    실패 : 물리적 접속 실패.
//                    보통 토스트 띄우는 것으로 대체함.

                }

                override fun onResponse(call: Call, response: Response) {

//                    결과가 무엇이든 응답은 돌아온 상황.

//                    응답의 본문(body)에 어떤 내용? -> 본문만 String으로 변환.

                    val bodyString = response.body!!.string()

//                    bodyString은 JSON 양식으로 가공됨. => 한글도 임시 변환된 상태 (encoding)

//                    일반 String -> JSONObject로 변환 (한글도 원상복구)
                    val jsonObj = JSONObject(bodyString)

                    Log.d("서버응답", jsonObj.toString())

//                    나를 호출한 화면에게 jsonObj를 처리하는 일처리를 미루자.
                    handler?.onResponse(jsonObj)


                }


            })

        }

//        회원가입 함수 - PUT

        fun putRequestSignUp(
            email: String,
            pw: String,
            nickname: String,
            handler: JsonResponseHandler?
        ) {

            val urlString = "${HOST_URL}/user"

            val formData = FormBody.Builder()
                .add("email", email)
                .add("password", pw)
                .add("nick_name", nickname)
                .build()

            val request = Request.Builder()
                .url(urlString)
                .put(formData)
                .build()

            val client = OkHttpClient()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {

                }

                override fun onResponse(call: Call, response: Response) {

                    val bodyString = response.body!!.string()
                    val jsonObj = JSONObject(bodyString)
                    Log.d("서버응답", jsonObj.toString())
                    handler?.onResponse(jsonObj)

                }

            })


        }


//        중복 확인 함수 - GET

        fun getRequestDuplCheck(type: String, value: String, handler: JsonResponseHandler?) {

//            1. 어디로 가야? url + 어떤 파라미터 데이터? 같이 작성.

//           url을 만드는 과정이 복잡함 => 한단계씩 쌓아나가는 식으로 URL 작성.

            val urlBuilder =
                "${HOST_URL}/user_check".toHttpUrlOrNull()!!.newBuilder()  // 서버주소 / 기능주소 까지만.
            urlBuilder.addEncodedQueryParameter("type", type)
            urlBuilder.addEncodedQueryParameter("value", value)

//            최종 완성 주소 -> String으로 저장.

            val urlString = urlBuilder.toString()

            Log.d("완성주소", urlString)

//            3. 어떤 메소드 + 정보 종합 Request 생성

            val request = Request.Builder()
                .url(urlString)
                .get()
                .build()

//            실제 API 호출 - client

            val client = OkHttpClient()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {

                }

                override fun onResponse(call: Call, response: Response) {
                    val bodyString = response.body!!.string()
                    val jsonObj = JSONObject(bodyString)
                    Log.d("서버응답", jsonObj.toString())
                    handler?.onResponse(jsonObj)
                }


            })


        }

//        연습 - 내 정보 가져오기 (토큰 첨부) - GET

        fun getRequestMyInfo(context: Context, handler: JsonResponseHandler?) {

            val urlBuilder =
                "${HOST_URL}/user_info".toHttpUrlOrNull()!!.newBuilder()  // 서버주소 / 기능주소 까지만.
//            urlBuilder.addEncodedQueryParameter("type", type)
//            urlBuilder.addEncodedQueryParameter("value", value)

            val urlString = urlBuilder.toString()

            Log.d("완성주소", urlString)

//            Request를 만들 때 헤더도 같이 첨부

            val request = Request.Builder()
                .url(urlString)
                .get()
                .header("X-Http-Token", ContextUtil.getToken(context))
                .build()

//            실제 API 호출 - client

            val client = OkHttpClient()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {

                }

                override fun onResponse(call: Call, response: Response) {
                    val bodyString = response.body!!.string()
                    val jsonObj = JSONObject(bodyString)
                    Log.d("서버응답", jsonObj.toString())
                    handler?.onResponse(jsonObj)
                }


            })


        }

//        메인화면의 데이터 가져오기 (토론 주제 목록 가져오기) - GET

        fun getRequestMainInfo(context: Context, handler: JsonResponseHandler?) {

            val urlBuilder =
                "${HOST_URL}/v2/main_info".toHttpUrlOrNull()!!.newBuilder()  // 서버주소 / 기능주소 까지만.
//            urlBuilder.addEncodedQueryParameter("type", type)
//            urlBuilder.addEncodedQueryParameter("value", value)

            val urlString = urlBuilder.toString()

            Log.d("완성주소", urlString)

//            Request를 만들 때 헤더도 같이 첨부

            val request = Request.Builder()
                .url(urlString)
                .get()
                .header("X-Http-Token", ContextUtil.getToken(context))
                .build()

//            실제 API 호출 - client

            val client = OkHttpClient()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {

                }

                override fun onResponse(call: Call, response: Response) {
                    val bodyString = response.body!!.string()
                    val jsonObj = JSONObject(bodyString)
                    Log.d("서버응답", jsonObj.toString())
                    handler?.onResponse(jsonObj)
                }


            })


        }

//            토론 주제별 상세 조회하기 - GET

        fun getRequestTopicDetail(
            context: Context,
            topicId: Int, orderType: String,
            handler: JsonResponseHandler?
        ) {

            val urlBuilder =
                "${HOST_URL}/topic".toHttpUrlOrNull()!!.newBuilder()  // 서버주소 / 기능주소 까지만.

//            주소 양식 : Path - /topic/3  => /3 PathSegment라고 부름.
//            주소 양식 : Query - /topic?name=조경진   QueryParameter 라고 부름.
            urlBuilder.addPathSegment(topicId.toString())

            urlBuilder.addEncodedQueryParameter("order_type", orderType)
//            urlBuilder.addEncodedQueryParameter("value", value)


            val urlString = urlBuilder.toString()

            Log.d("완성주소", urlString)


//            Request를 만들때 헤더도 같이 첨부.

            val request = Request.Builder()
                .url(urlString)
                .get()
                .header("X-Http-Token", ContextUtil.getToken(context))
                .build()


//            실제 API 호출 - client

            val client = OkHttpClient()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {

                }

                override fun onResponse(call: Call, response: Response) {
                    val bodyString = response.body!!.string()
                    val jsonObj = JSONObject(bodyString)
                    Log.d("서버응답", jsonObj.toString())
                    handler?.onResponse(jsonObj)
                }


            })


        }

//        토론 진영에 투표하기

        fun postRequestVote(context: Context, sideId: Int, handler: JsonResponseHandler?) {

//            1. 어디로 가야? URL

            val urlString = "${HOST_URL}/topic_vote"

//            2. 어떤 데이터를 들고가는가? (파라미터)

            val formData = FormBody.Builder()
                .add("side_id", sideId.toString())
                .build()

//            3. 어떤 메소드 + 1/2 데이터 결합 => 어떤 요청인지 완성

            val request = Request.Builder()
                .url(urlString)
                .post(formData)
                .header("X-Http-Token", ContextUtil.getToken(context))
                .build()

//            4. 완성된 Request를 실제로 호출 => 클라이언트 역할.

            val client = OkHttpClient()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {

//                    실패 : 물리적 접속 실패.
//                    보통 토스트 띄우는 것으로 대체함.

                }

                override fun onResponse(call: Call, response: Response) {

//                    결과가 무엇이든 응답은 돌아온 상황.

//                    응답의 본문(body)에 어떤 내용? -> 본문만 String으로 변환.

                    val bodyString = response.body!!.string()

//                    bodyString은 JSON 양식으로 가공됨. => 한글도 임시 변환된 상태 (encoding)

//                    일반 String -> JSONObject로 변환 (한글도 원상복구)
                    val jsonObj = JSONObject(bodyString)

                    Log.d("서버응답", jsonObj.toString())

//                    나를 호출한 화면에게 jsonObj를 처리하는 일처리를 미루자.
                    handler?.onResponse(jsonObj)


                }


            })

        }

//        댓글에 좋아요 / 싫어요 찍기

        fun postRequestReplyLikeOrDislike(context: Context, replyId: Int, isLike : Boolean, handler: JsonResponseHandler?) {

//            1. 어디로 가야? URL

            val urlString = "${HOST_URL}/topic_reply_like"

//            2. 어떤 데이터를 들고가는가? (파라미터)

            val formData = FormBody.Builder()
                .add("reply_id", replyId.toString())
                .add("is_like", isLike.toString())
                .build()

//            3. 어떤 메소드 + 1/2 데이터 결합 => 어떤 요청인지 완성

            val request = Request.Builder()
                .url(urlString)
                .post(formData)
                .header("X-Http-Token", ContextUtil.getToken(context))
                .build()

//            4. 완성된 Request를 실제로 호출 => 클라이언트 역할.

            val client = OkHttpClient()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {

//                    실패 : 물리적 접속 실패.
//                    보통 토스트 띄우는 것으로 대체함.

                }

                override fun onResponse(call: Call, response: Response) {

//                    결과가 무엇이든 응답은 돌아온 상황.

//                    응답의 본문(body)에 어떤 내용? -> 본문만 String으로 변환.

                    val bodyString = response.body!!.string()

//                    bodyString은 JSON 양식으로 가공됨. => 한글도 임시 변환된 상태 (encoding)

//                    일반 String -> JSONObject로 변환 (한글도 원상복구)
                    val jsonObj = JSONObject(bodyString)

                    Log.d("서버응답", jsonObj.toString())

//                    나를 호출한 화면에게 jsonObj를 처리하는 일처리를 미루자.
                    handler?.onResponse(jsonObj)


                }


            })

        }

//        토론에 의견 (주제에 바로 댓글) 달기

        fun postRequestTopicReply(context: Context, topicId: Int, content: String, handler: JsonResponseHandler?) {

//            1. 어디로 가야? URL

            val urlString = "${HOST_URL}/topic_reply"

//            2. 어떤 데이터를 들고가는가? (파라미터)

            val formData = FormBody.Builder()
                .add("topic_id", topicId.toString())
                .add("content", content)
                .build()

//            3. 어떤 메소드 + 1/2 데이터 결합 => 어떤 요청인지 완성

            val request = Request.Builder()
                .url(urlString)
                .post(formData)
                .header("X-Http-Token", ContextUtil.getToken(context))
                .build()

//            4. 완성된 Request를 실제로 호출 => 클라이언트 역할.

            val client = OkHttpClient()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {

//                    실패 : 물리적 접속 실패.
//                    보통 토스트 띄우는 것으로 대체함.

                }

                override fun onResponse(call: Call, response: Response) {

//                    결과가 무엇이든 응답은 돌아온 상황.

//                    응답의 본문(body)에 어떤 내용? -> 본문만 String으로 변환.

                    val bodyString = response.body!!.string()

//                    bodyString은 JSON 양식으로 가공됨. => 한글도 임시 변환된 상태 (encoding)

//                    일반 String -> JSONObject로 변환 (한글도 원상복구)
                    val jsonObj = JSONObject(bodyString)

                    Log.d("서버응답", jsonObj.toString())

//                    나를 호출한 화면에게 jsonObj를 처리하는 일처리를 미루자.
                    handler?.onResponse(jsonObj)


                }


            })

        }

    }

}