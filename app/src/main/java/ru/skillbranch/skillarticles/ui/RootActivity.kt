package ru.skillbranch.skillarticles.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.switchmaterial.SwitchMaterial
import ru.skillbranch.skillarticles.R
import ru.skillbranch.skillarticles.extensions.dpToIntPx
import ru.skillbranch.skillarticles.ui.custom.ArticleSubmenu
import ru.skillbranch.skillarticles.ui.custom.Bottombar
import ru.skillbranch.skillarticles.ui.custom.CheckableImageView
import ru.skillbranch.skillarticles.viewmodels.ArticleState
import ru.skillbranch.skillarticles.viewmodels.ArticleViewModel
import ru.skillbranch.skillarticles.viewmodels.Notify
import ru.skillbranch.skillarticles.viewmodels.ViewModelFactory

class RootActivity : AppCompatActivity() {
    private lateinit var viewModel: ArticleViewModel

    private lateinit var btnSettings:CheckableImageView
    private lateinit var coordinatorContainer : CoordinatorLayout
    private lateinit var bottomBar : Bottombar
    private lateinit var btnLike : CheckableImageView
    private lateinit var btnBookmark : CheckableImageView
    private lateinit var btnShare : ImageView
    private lateinit var btnTextUp : CheckableImageView
    private lateinit var btnTextDown : CheckableImageView
    private lateinit var switchMode : SwitchMaterial
    private lateinit var toolbar : Toolbar
    private lateinit var submenu : ArticleSubmenu
    private lateinit var tvTextContent : TextView

    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)
        coordinatorContainer  = findViewById<CoordinatorLayout>(R.id.coordinator_container)
        bottomBar  = findViewById<Bottombar>(R.id.bottombar)
        btnLike = findViewById<CheckableImageView>(R.id.btn_like)
        btnBookmark = findViewById<CheckableImageView>(R.id.btn_boolmark)
        btnShare = findViewById<ImageView>(R.id.btn_share)
        btnLike.setOnClickListener{viewModel.handleLike()}
        btnTextUp = findViewById<CheckableImageView>(R.id.btn_text_up)
        btnTextDown = findViewById<CheckableImageView>(R.id.btn_text_down)
        switchMode = findViewById<SwitchMaterial>(R.id.switch_mode)
        toolbar  = findViewById(R.id.toolbar)
        btnSettings = findViewById<CheckableImageView>(R.id.btn_settings)
        submenu = findViewById<ArticleSubmenu>(R.id.submenu)
        tvTextContent = findViewById(R.id.tv_text_content)
        setupToolbar()
        setupBottombar()
       setupSubmenu()




        val vmFactory = ViewModelFactory("0")
        viewModel = ViewModelProviders.of(this, vmFactory).get(ArticleViewModel::class.java)
        viewModel.observeState(this){
            renderUi(it)
            setupToolbar()
        }
        viewModel.observeNotifications(this){
            renderNotifications(it)
        }
    }

    private fun renderNotifications(notify: Notify) {
        val snackbar = Snackbar.make(coordinatorContainer, notify.message, Snackbar.LENGTH_SHORT)
        when(notify){
            is Notify.TextMessage -> {}
            is Notify.ActionMessage -> {
                snackbar.setActionTextColor(getColor(R.color.color_accent_dark))
                snackbar.setAction(notify.actionLabel){
                    notify.actionHandler?.invoke()
                }
            }
            is Notify.ErrorMessage -> {
                with(snackbar){
                    setBackgroundTint(getColor(R.color. design_default_color_error))
                    setTextColor(getColor(android.R.color.white))
                    setActionTextColor(getColor(android.R.color.white))
                    setAction(notify.errLabel){
                        notify.errHandler?.invoke()
                    }
                }
            }

        }
        snackbar.show()
    }

    private fun setupSubmenu() {

        btnTextUp.setOnClickListener{viewModel.handleUpText()}
        btnTextDown.setOnClickListener{viewModel.handleDownText()}
        switchMode.setOnClickListener{viewModel.handleNightMode()}

    }

    private fun setupBottombar() {
        btnLike.setOnClickListener{viewModel.handleLike()}
        btnBookmark.setOnClickListener{viewModel.handleBookmark()}
        btnShare.setOnClickListener{viewModel.handleShare()}
        btnSettings.setOnClickListener{viewModel.handleToggleMenu()}

    }

    private fun renderUi(data: ArticleState) {
        btnSettings.isChecked = data.isShowMenu
        if (data.isShowMenu) submenu.open() else submenu.close()
        btnLike.isChecked = data.isLike
        btnBookmark.isChecked = data.isBookmark
        switchMode.isChecked = data.isDarkMode
        delegate.localNightMode = if (data.isDarkMode) AppCompatDelegate.MODE_NIGHT_YES
        else AppCompatDelegate.MODE_NIGHT_NO
        if (data.isBigText){
            tvTextContent.textSize = 18f
            btnTextUp.isChecked = true
            btnTextDown.isChecked = false
        }else{
            tvTextContent.textSize = 14f
            btnTextUp.isChecked = false
            btnTextDown.isChecked = true
        }

        tvTextContent.text = if (data.isLoadingContent) "loading" else data.content.first() as String
        toolbar.title = data.title ?: "Skill Articles"
        toolbar.subtitle = data.category ?:"loading..."
        if (data.categoryIcon !=null) toolbar.logo = getDrawable(data.categoryIcon as Int)





    }

    private fun setupToolbar() {

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val logo = if (toolbar.childCount >2) toolbar.getChildAt(2) as ImageView else null
        logo?.scaleType = ImageView.ScaleType.CENTER_CROP
        (logo?.layoutParams as? Toolbar.LayoutParams)?.let {
            it.width = this.dpToIntPx(40)
            it.height = this.dpToIntPx(40)
            it.marginEnd = this.dpToIntPx(16)
            logo.layoutParams = it
        }

    }
}