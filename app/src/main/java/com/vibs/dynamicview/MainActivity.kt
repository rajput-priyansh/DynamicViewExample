package com.vibs.dynamicview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.vibs.dynamicview.models.ResponseViews
import com.vibs.dynamicview.models.TestJsonItem
import android.widget.RelativeLayout




class MainActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private lateinit var progressBar: ProgressBar
    private lateinit var scrollView: ScrollView
    private lateinit var llContainer: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUi()
        observer()
        getViewList()
    }

    /**
     * init All UI
     */
    private fun initUi() {
        progressBar = findViewById(R.id.progressBar)
        scrollView = findViewById(R.id.scrollView)
        llContainer = findViewById(R.id.llContainer)
    }

    /**
     * User to Observ all live data
     */
    private fun observer() {
        viewModel.viewResponse.observe(this, {
            Log.d("TESTP", "observer() called:$it")
            if (it?.testJson == null)
                return@observe

            llContainer.removeAllViews()
            for (data in it.testJson) {
                bindDynamicView(data!!, llContainer)
            }
        })
    }

    /**
     * Bind the all Dynamic views
     */
    private fun bindDynamicView(data: TestJsonItem, parent: ViewGroup) {

        val padding = data.padding?.toIntOrNull() ?: 0
        val width = data.layoutWidth?.toIntOrNull() ?: ViewGroup.LayoutParams.WRAP_CONTENT
        val marginTop = data.layoutMarginTop?.toIntOrNull() ?: 0
        val progress = data.progress?.toIntOrNull() ?: 0
        val max = data.max ?: 0
        val text = data.text ?: ""
        val gravity = if (data.gravity == "center") Gravity.CENTER else if (data.gravity == "left") Gravity.LEFT else if (data.gravity == "right") Gravity.RIGHT else Gravity.NO_GRAVITY


        val params = LinearLayout.LayoutParams(
            width,ViewGroup.LayoutParams.WRAP_CONTENT
        )
        params.topMargin = marginTop

        when (data.type) {
            "LinearLayout" -> {
                val linearLayout = LinearLayout(this@MainActivity)

                linearLayout.layoutParams = LinearLayout.LayoutParams(
                    width, ViewGroup.LayoutParams.WRAP_CONTENT
                )

                linearLayout.orientation =
                    if (data.orientation == "vertical") LinearLayout.VERTICAL else LinearLayout.HORIZONTAL

                if (!data.padding.isNullOrEmpty()) {
                    linearLayout.setPadding(padding, padding, padding, padding)
                }

                if (data.children != null && data.children.isNotEmpty()) {
                    for (child in data.children) {
                        if (child != null)
                            bindDynamicView(child, linearLayout)
                    }
                }

                parent.addView(linearLayout)

            }
            "TextView" -> {
                val textView = TextView(this@MainActivity)
                textView.setPadding(padding, padding, padding, padding)
                textView.text = text
                textView.layoutParams = params
                textView.gravity = gravity

                parent.addView(textView)
            }
            "HorizontalProgressBar" -> {
                val progressBar = ProgressBar(
                    this@MainActivity,
                    null,
                    android.R.attr.progressBarStyleHorizontal
                )
                progressBar.setPadding(padding, padding, padding, padding)
                progressBar.layoutParams = params
                progressBar.max = max
                progressBar.progress = progress
            }
            "RelativeLayout" -> {

                val relativeLayout = RelativeLayout(this@MainActivity)

                relativeLayout.layoutParams = RelativeLayout.LayoutParams(
                    width, ViewGroup.LayoutParams.WRAP_CONTENT
                )

                if (!data.padding.isNullOrEmpty()) {
                    relativeLayout.setPadding(padding, padding, padding, padding)
                }

                if (data.children != null && data.children.isNotEmpty()) {
                    for (child in data.children) {
                        if (child != null)
                            bindDynamicView(child, relativeLayout)
                    }
                }

                parent.addView(relativeLayout)
            }
            "ImageView" -> {
            }
            "Button" -> {
                val button = Button(this@MainActivity)

                button.setPadding(padding, padding, padding, padding)
                button.text = text
                button.layoutParams = params
                button.gravity = gravity

                parent.addView(button)

            }
        }
    }

    /**
     * Make API call
     */
    private fun getViewList() {
        progressBar.visibility = View.VISIBLE
        scrollView.visibility = View.GONE
        viewModel.getMovies().observe(this, {
            progressBar.visibility = View.GONE
            scrollView.visibility = View.VISIBLE
            if (it != null) {
                viewModel.setResponseView(it)
            } else {
                Toast.makeText(this@MainActivity, "Error", Toast.LENGTH_SHORT).show()
            }
        })
    }
}