package com.testRetrofit.unsplash_app_tutorial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import com.jeongdaeri.unsplash_app_tutorial.R
import com.testRetrofit.unsplash_app_tutorial.retrofit.RetrofitManager
import com.testRetrofit.unsplash_app_tutorial.utils.Constants.TAG
import com.testRetrofit.unsplash_app_tutorial.utils.RESPONSE_STATUS
import com.testRetrofit.unsplash_app_tutorial.utils.SEARCH_TYPE
import com.testRetrofit.unsplash_app_tutorial.utils.onMyTextChanged
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_button_search.*

class MainActivity : AppCompatActivity() {
    private var currentSearchType: SEARCH_TYPE = SEARCH_TYPE.PHOTO
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG, "MainActivity - onCreate() called")


        // 텍스트가 변경이 되었을때
        search_term_edit_text.onMyTextChanged {

            // 입력된 글자가 하나라도 있다면
            if (it.toString().count() > 0) {
                // 검색 버튼을 보여준다.
                frame_search_btn.visibility = View.VISIBLE
                search_term_text_layout.helperText = " "


                // 스크롤뷰를 올린다.
                main_scrollview.scrollTo(0, 200)

            } else {
                frame_search_btn.visibility = View.INVISIBLE
                search_term_text_layout.helperText = "검색어를 입력해주세요"
            }

            if (it.toString().count() == 12) {
                Log.d(TAG, "MainActivity - 에러 띄우기 ")
                Toast.makeText(this, "검색어는 12자 까지만 입력 가능합니다.", Toast.LENGTH_SHORT).show()
            }

        }

        // 검색 버튼 클릭시
        btn_search.setOnClickListener {
            Log.d(TAG, "MainActivity - 검색 버튼이 클릭되었다. / currentSearchType : $currentSearchType")

            this.handleSearchButtonUi()

            val userSearchInput = search_term_edit_text.text.toString()

            // 검색 api 호출
            RetrofitManager.instance.searchPhotos(
                searchTerm = search_term_edit_text.text.toString(),
                completion = { responseState, responseDataArrayList ->

                    when (responseState) {
                        RESPONSE_STATUS.OKAY -> {
                            Log.d(TAG, "api 호출 성공 : ${responseDataArrayList?.size}")

                            val intent = Intent(this, PhotoCollectionActivity::class.java)

                            val bundle = Bundle()

                            bundle.putSerializable("photo_array_list", responseDataArrayList)

                            intent.putExtra("array_bundle", bundle)

                            intent.putExtra("search_term", userSearchInput)

                            startActivity(intent)

                        }

                        RESPONSE_STATUS.FAIL -> {
                            Toast.makeText(this, "api 호출 에러입니다.", Toast.LENGTH_SHORT).show()
                            Log.d(TAG, "api 호출 실패 : $responseDataArrayList")
                        }

                        RESPONSE_STATUS.NO_CONTENT -> {
                            Toast.makeText(this, "검색결과가 없습니다.", Toast.LENGTH_SHORT).show()
                        }
                    }

                    btn_progress.visibility = View.INVISIBLE
                    btn_search.text = "검색"
                    search_term_edit_text.setText("")

                })


        }


    }


    private fun handleSearchButtonUi() {

        btn_progress.visibility = View.VISIBLE

        btn_search.text = ""
    }
}
