package com.raywenderlich.learn.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.raywenderlich.learn.ui.R

@Composable
public actual fun icBrand(): Painter = painterResource(R.drawable.ic_brand)

@Composable
public actual fun icLauncher(): Painter = painterResource(R.mipmap.ic_launcher)

@Composable
public actual fun icMore(): Painter = painterResource(R.drawable.ic_more)

@Composable
public actual fun icHome(): Painter = painterResource(R.drawable.ic_home)

@Composable
public actual fun icBookmark(): Painter = painterResource(R.drawable.ic_bookmarks)

@Composable
public actual fun icLatest(): Painter = painterResource(R.drawable.ic_latest)

@Composable
public actual fun icSearch(): Painter = painterResource(R.drawable.ic_search)