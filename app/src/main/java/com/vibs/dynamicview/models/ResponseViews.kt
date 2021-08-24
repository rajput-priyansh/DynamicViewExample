package com.vibs.dynamicview.models

import com.google.gson.annotations.SerializedName


data class ResponseViews(

	@field:SerializedName("testJson")
	val testJson: List<TestJsonItem?>? = null
)


data class TestJsonItem(

	@field:SerializedName("padding")
	val padding: String? = null,

	@field:SerializedName("orientation")
	val orientation: String? = null,

	@field:SerializedName("children")
	val children: List<TestJsonItem?>? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("layout_width")
	val layoutWidth: String? = null,

	@field:SerializedName("gravity")
	val gravity: String? = null,

	@field:SerializedName("text")
	val text: String? = null,

	@field:SerializedName("max")
	val max: Int? = null,

	@field:SerializedName("progress")
	val progress: String? = null,

	@field:SerializedName("layout_marginTop")
	val layoutMarginTop: String? = null,

)
