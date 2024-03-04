package com.innovativetools.ai.myaiassistant.ui.myassistants

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.innovativetools.ai.myaiassistant.utils.Constants
import com.innovativetools.ai.myaiassistant.components.AssistantCard
import com.innovativetools.ai.myaiassistant.data.model.AiAssistantModel
import com.innovativetools.ai.myaiassistant.data.model.AiAssistantsModel
import com.innovativetools.ai.myaiassistant.R
import com.innovativetools.ai.myaiassistant.components.MainAppBar
import com.innovativetools.ai.myaiassistant.components.NoInternetDialog
import com.innovativetools.ai.myaiassistant.components.TabItem
import com.innovativetools.ai.myaiassistant.ui.activity.isOnline
import com.innovativetools.ai.myaiassistant.utils.showInterstitial
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale


//fun loadAssistants(context: Context): List<Assistant> {
//    val jsonString: String
//    try {
//        jsonString = context.assets.open("assistants.json").bufferedReader().use { it.readText() }
//    } catch (ioException: IOException) {
//        ioException.printStackTrace()
//        return emptyList()
//    }
//    val gson = Gson()
//    val listAssistantType = object : TypeToken<List<Assistant>>() {}.type
//    return gson.fromJson(jsonString, listAssistantType)
//}

@SuppressLint("SuspiciousIndentation")
@Composable
fun MyAssistantsScreen(
    navigateToChat: (String, String, String, List<String>) -> Unit,
    navigateToUpgrade: () -> Unit,
    viewModel: MyAssistantsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var isClickEnabled by remember { mutableStateOf(true) }
    fun changeLanguage(context: Context, language: String) {
    val locale = Locale(language)
    Locale.setDefault(locale)
    val config = Configuration()
    config.setLocale(locale)
    context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }

    LaunchedEffect(Unit) {
        viewModel.getCurrentLanguageCode()
        val currentLangCode = viewModel.currentLanguageCode.value
        changeLanguage(context, currentLangCode)
    }

    val currentLanguageCode = viewModel.currentLanguageCode.value


    val languageNames = mapOf(
        "en" to "English",
        "ar" to "Arabic",
        "de" to "Deutsch",
        "hi" to "Hindi",
        "it" to "Italian",
        "ja" to "Japanese",
        "ko" to "Korean",
        "mr" to "Marathi",
        "no" to "Norwegian (Norsk)",
        "ro" to "Romanian",
        "sv" to "Svenska",
    )

    val assistantList: List<AiAssistantsModel> = listOf(
        AiAssistantsModel(
            title = stringResource(R.string.social_media),
            assistant = listOf(
                AiAssistantModel(
                    image = R.drawable.ic_twitter,
                    name = stringResource(R.string.twitter),
                    description = stringResource(R.string.twitter_description),
                    role = Constants.SocialMedia.TWITTER + "- output should be in language ${languageNames[currentLanguageCode]}",
                    instruction = stringResource(id = R.string.twitter_instruction),
                    exampleList1 = listOf(
                        stringResource(R.string.twitter_example1),
                        stringResource(R.string.twitter_example2),
                    )
                ),

                AiAssistantModel(
                    image = R.drawable.ic_instagram,
                    name = stringResource(R.string.instagram),
                    description = stringResource(R.string.instagram_description),
                    role = Constants.SocialMedia.INSTAGRAM + "- output should be in language ${languageNames[currentLanguageCode]}",
                    instruction = stringResource(id = R.string.instagram_instruction),
                    exampleList1 = listOf(
                        stringResource(R.string.instagram_example1),
                        stringResource(R.string.instagram_example4),
                        stringResource(R.string.instagram_example3),
                        stringResource(R.string.instagram_example2),
                    )
                ),


                AiAssistantModel(
                    image = R.drawable.ic_linkedin,
                    name = stringResource(R.string.linkedin),
                    description = stringResource(R.string.linkedin_description),
                    role = Constants.SocialMedia.LINKEDIN + "- output should be in language ${languageNames[currentLanguageCode]}",
                    instruction = stringResource(id = R.string.linkedin_instruction),
                    exampleList1 = listOf(
                        stringResource(R.string.linkedin_example1),
                        stringResource(R.string.linkedin_example2),
                    )
                ),



                AiAssistantModel(
                    image = R.drawable.ic_tiktok,
                    name = stringResource(R.string.tiktok),
                    description = stringResource(R.string.tiktok_description),
                    role = Constants.SocialMedia.TIKTOK + "- output should be in language ${languageNames[currentLanguageCode]}",
                    instruction = stringResource(id = R.string.tiktok_instruction),
                    exampleList1 = listOf(
                        stringResource(R.string.tiktok_example1),
                        stringResource(R.string.tiktok_example2),
                    )
                ),

                AiAssistantModel(
                    image = R.drawable.laugh,
                    name = stringResource(R.string.meme_maker),
                    description = stringResource(R.string.meme_desc),
                    role = Constants.SocialMedia.MEME_MAKER + "- output should be in language ${languageNames[currentLanguageCode]}",
                    instruction = stringResource(id = R.string.meme_instruction),
                    exampleList1 = listOf(
                        stringResource(R.string.meme_example),
                        stringResource(R.string.meme_example1),
                        stringResource(R.string.meme_example2),
                    )
                ),

                AiAssistantModel(
                    image = R.drawable.script,
                    name = stringResource(R.string.bio_maker),
                    description = stringResource(R.string.bio_desc),
                    role = Constants.SocialMedia.BIO_MAKER + "- output should be in language ${languageNames[currentLanguageCode]}",
                    instruction = stringResource(id = R.string.bio_instruction),
                    exampleList1 = listOf(
                        stringResource(R.string.bio_example),
                        stringResource(R.string.bio_example1),
                    )
                ),

                AiAssistantModel(
                    image = R.drawable.poll,
                    name = stringResource(R.string.poll_maker),
                    description = stringResource(R.string.poll_desc),
                    role = Constants.SocialMedia.POLL_MAKER + "- output should be in language ${languageNames[currentLanguageCode]}",
                    instruction = stringResource(id = R.string.poll_instruction),
                    exampleList1 = listOf(
                        stringResource(R.string.poll_example),
                        stringResource(R.string.poll_example1),
                    ),
                ),

                AiAssistantModel(
                    image = R.drawable.quote,
                    name = stringResource(R.string.quote_maker),
                    description = stringResource(R.string.quote_instruction),
                    role = Constants.SocialMedia.QUOTE_GENERATOR + "- output should be in language ${languageNames[currentLanguageCode]}",
                    instruction = stringResource(id = R.string.quote_instruction),
                    exampleList1 = listOf(
                        stringResource(R.string.quote_example),
                        stringResource(R.string.quote_example1),
                        stringResource(R.string.quote_example2),
                        stringResource(R.string.quote_example3),
                    )
                ),

                AiAssistantModel(
                    image = R.drawable.laugh,
                    name = stringResource(R.string.witty_reply),
                    description = stringResource(R.string.witty_reply_desc),
                    role = Constants.SocialMedia.WITTY_REPLIES + "- output should be in language ${languageNames[currentLanguageCode]}",
                    instruction = stringResource(id = R.string.witty_reply_instruction),
                    exampleList1 = listOf(
                        stringResource(R.string.witty_reply_example),
                        stringResource(R.string.witty_reply_example1),
                        stringResource(R.string.witty_reply_example2),
                    ),
                ),

                AiAssistantModel(
                    image = R.drawable.ic_youtube,
                    name = stringResource(R.string.youtube),
                    description = stringResource(R.string.youtube_description),
                    role = Constants.SocialMedia.YOUTUBE_SCRIPT + "- output should be in language ${languageNames[currentLanguageCode]}",
                    instruction = stringResource(id = R.string.youtube_script_instruction),
                    exampleList1 = listOf(
                        stringResource(R.string.youtube_example1),
                        stringResource(R.string.youtube_example2),
                    )
                ),

                AiAssistantModel(
                    image = R.drawable.ic_youtube,
                    name = stringResource(R.string.youtube_idea),
                    description = stringResource(R.string.youtube_idea_description),
                    role = Constants.SocialMedia.YOUTUBE_CONTENT_IDEA + "- output should be in language ${languageNames[currentLanguageCode]}",
                    instruction = stringResource(id = R.string.youtube_idea_instruction),
                    exampleList1 = listOf(
                        stringResource(R.string.youtube_idea_example),
                        stringResource(R.string.youtube_idea_example1),
                        stringResource(R.string.youtube_idea_example2),
                    )
                ),

                AiAssistantModel(
                    image = R.drawable.script,
                    name = stringResource(R.string.story_time),
                    description = stringResource(R.string.story_time_desc),
                    role = Constants.SocialMedia.STORY_TIME + "- output should be in language ${languageNames[currentLanguageCode]}",
                    instruction = stringResource(id = R.string.story_time_instruction),
                    exampleList1 = listOf(
                        stringResource(R.string.story_time_example),
                        stringResource(R.string.story_time_example1),
                        stringResource(R.string.story_time_example2),
                        stringResource(R.string.story_time_example3),
                    )
                ),
            )
        ),


        AiAssistantsModel(
            title = stringResource(R.string.academics), assistant = listOf(
                AiAssistantModel(
                    image = R.drawable.academic,
                    name = stringResource(R.string.essay_writing),
                    description = stringResource(R.string.essay_desc),
                    role = Constants.Education.ESSAY_WRITING + "- output should be in language ${languageNames[currentLanguageCode]}",
                    instruction = stringResource(id = R.string.essay_instruction),
                    exampleList1 = listOf(
                        stringResource(R.string.essay_example),
                        stringResource(R.string.essay_example1),
                        stringResource(R.string.essay_example2),
                    ),
                ),

                AiAssistantModel(
                    image = R.drawable.otliner,
                    name = stringResource(R.string.summarizer),
                    description = stringResource(R.string.summarizer_desc),
                    role = Constants.Education.SUMMARIZER + "- output should be in language ${languageNames[currentLanguageCode]}",
                    instruction = stringResource(id = R.string.summarizer_instruction),
                    exampleList1 = listOf(
                        stringResource(R.string.summarizer_example),
                        stringResource(R.string.summarizer_example1),
                    ),
                ),

                AiAssistantModel(
                    image = R.drawable.translate,
                    name = stringResource(R.string.lang_translation),
                    description = stringResource(R.string.translation_desc),
                    role = Constants.Education.LANGUAGE_TRANSLATION,
                    instruction = stringResource(id = R.string.translation_instruction),
                    exampleList1 = listOf(
                        stringResource(R.string.translation_example),
                        stringResource(R.string.translation_example1),
                        stringResource(R.string.translation_example2),
                    ),
                ),

                AiAssistantModel(
                    image = R.drawable.script,
                    name = stringResource(R.string.paraphrase),
                    description = stringResource(R.string.paraphrase_desc),
                    role = Constants.Education.PARAPHRASING + "- output should be in language ${languageNames[currentLanguageCode]}",
                    instruction = stringResource(id = R.string.paraphrase_instruction),
                    exampleList1 = listOf(
                        stringResource(R.string.paraphrase_example),
                        stringResource(R.string.paraphrase_example1),
                        stringResource(R.string.paraphrase_example2),
                    ),
                ),

                AiAssistantModel(
                    image = R.drawable.tutor,
                    name = stringResource(R.string.roadmap),
                    description = stringResource(R.string.roadmap_desc),
                    role = Constants.Education.ROADMAP + "- output should be in language ${languageNames[currentLanguageCode]}",
                    instruction = stringResource(id = R.string.roadmap_instruction),
                    exampleList1 = listOf(
                        stringResource(R.string.roadmap_example),
                        stringResource(R.string.roadmap_example1),
                        stringResource(R.string.roadmap_example2),
                    ),
                ),

                AiAssistantModel(
                    image = R.drawable.quizmaker,
                    name = stringResource(R.string.quiz),
                    description = stringResource(R.string.quiz_desc),
                    role = Constants.Education.QUIZ_MAKER + "- output should be in language ${languageNames[currentLanguageCode]}",
                    instruction = stringResource(id = R.string.quiz_instruction),
                    exampleList1 = listOf(
                        stringResource(R.string.quiz_example),
                        stringResource(R.string.quiz_example1),
                        stringResource(R.string.quiz_example2),
                    ),
                ),

                AiAssistantModel(
                    image = R.drawable.mentor,
                    name = stringResource(R.string.tutor),
                    description = stringResource(R.string.tutor_desc),
                    role = Constants.Education.TUTOR + "- output should be in language ${languageNames[currentLanguageCode]}",
                    instruction = stringResource(id = R.string.tutor_instruction),
                    exampleList1 = listOf(
                        stringResource(R.string.tutor_example),
                        stringResource(R.string.tutor_example1),
                        stringResource(R.string.tutor_example2),
                        stringResource(R.string.tutor_example3),
                    ),
                ),


                AiAssistantModel(
                    image = R.drawable.academic,
                    name = stringResource(R.string.proverbe),
                    description = stringResource(R.string.proverbe_desc),
                    role = Constants.Education.PROVERBS + "- output should be in language ${languageNames[currentLanguageCode]}",
                    instruction = stringResource(id = R.string.proverbe_instruction),
                    exampleList1 = listOf(
                        stringResource(R.string.proverbe_example),
                        stringResource(R.string.proverbe_example1),
                        stringResource(R.string.proverbe_example2),
                    ),
                ),

                AiAssistantModel(
                    image = R.drawable.ic_research,
                    name = stringResource(R.string.researcher),
                    description = stringResource(R.string.researcher_desc),
                    role = Constants.Education.RESEARCHER + "- output should be in language ${languageNames[currentLanguageCode]}",
                    instruction = stringResource(id = R.string.researcher_instruction),
                    exampleList1 = listOf(
                        stringResource(R.string.researcher_example),
                        stringResource(R.string.researcher_example1),
                        stringResource(R.string.researcher_example2),
                    ),
                ),
            )
        ),


        //Career
        AiAssistantsModel(
            title = stringResource(R.string.career), assistant = listOf(
                AiAssistantModel(
                    image = R.drawable.ic_resume,
                    name = stringResource(R.string.resume_builder),
                    description = stringResource(R.string.resume_builder_description),
                    instruction = stringResource(id = R.string.resume_builder_instruction),
                    role = Constants.Career.RESUME_BUILDER + "- output should be in language ${languageNames[currentLanguageCode]}",
                    exampleList1 = listOf(
                        stringResource(R.string.resume_builder_example1),
                        stringResource(R.string.resume_builder_example2),
                    ),

                    ),

                AiAssistantModel(
                    image = R.drawable.ic_interviewers,
                    name = stringResource(R.string.job_interview),
                    description = stringResource(R.string.job_interview_description),
                    role = Constants.Career.INTERVIEW_PREPARATION + "- output should be in language ${languageNames[currentLanguageCode]}",
                    instruction = stringResource(id = R.string.job_interview_instruction),
                    exampleList1 = listOf(
                        stringResource(R.string.job_interview_example1),
                        stringResource(R.string.job_interview_example2),
                    ),
                ),

                AiAssistantModel(
                    image = R.drawable.script,
                    name = stringResource(R.string.fiver_gig),
                    description = stringResource(R.string.fiver_gig_description),
                    role = Constants.Career.FIVERR_DESCRIPTION + "- output should be in language ${languageNames[currentLanguageCode]}",
                    instruction = stringResource(id = R.string.fiver_instruction),
                    exampleList1 = listOf(
                        stringResource(R.string.fiver_description_example1),
                        stringResource(R.string.fiver_description_example2),
                    ),

                    ),

                AiAssistantModel(
                    image = R.drawable.script,
                    name = stringResource(R.string.upwork_proposal),
                    description = stringResource(R.string.upwork_proposal_description),
                    role = Constants.Career.UPWORK_PROPOSAL + "- output should be in language ${languageNames[currentLanguageCode]}",
                    instruction = stringResource(id = R.string.upwork_proposal_instruction),
                    exampleList1 = listOf(
                        stringResource(R.string.upwork_proposal_example1),
                        stringResource(R.string.upwork_proposal_example2),
                    ),
                ),

                AiAssistantModel(
                    image = R.drawable.cover_letter,
                    name = stringResource(R.string.coverletter),
                    description = stringResource(R.string.coverletter_desc),
                    role = Constants.Career.COVER_LETTER + "- output should be in language ${languageNames[currentLanguageCode]}",
                    instruction = stringResource(id = R.string.coverletter_instruction),
                    exampleList1 = listOf(
                        stringResource(R.string.coverletter_example),
                        stringResource(R.string.coverletter_example1),
                    ),
                ),

                AiAssistantModel(
                    image = R.drawable.mentor,
                    name = stringResource(R.string.ccoach),
                    description = stringResource(R.string.ccoach_desc),
                    role = Constants.Career.CAREER_COACH + "- output should be in language ${languageNames[currentLanguageCode]}",
                    instruction = stringResource(id = R.string.ccoach_instruction),
                    exampleList1 = listOf(
                        stringResource(R.string.ccoach_example),
                        stringResource(R.string.ccoach_example1),
                    ),
                ),

                AiAssistantModel(
                    image = R.drawable.quizmaker,
                    name = stringResource(R.string.cquiz),
                    description = stringResource(R.string.cquiz_desc),
                    role = Constants.Career.CAREER_QUIZ + "- output should be in language ${languageNames[currentLanguageCode]}",
                    instruction = stringResource(id = R.string.cquiz_instruction),
                    exampleList1 = listOf(
                        stringResource(R.string.cquiz_example),
                        stringResource(R.string.cquiz_example1),
                    ),
                ),
            )
        ),


        //business
        AiAssistantsModel(
            title = stringResource(R.string.business), assistant = listOf(
                AiAssistantModel(
                    image = R.drawable.ic_product_description,
                    name = stringResource(R.string.product_description),
                    description = stringResource(R.string.product_description_desc),
                    instruction = stringResource(id = R.string.product_description_instruction),
                    role = Constants.Business.PRODUCT_DESCRIPTION + "- output should be in language ${languageNames[currentLanguageCode]}",
                    exampleList1 = listOf(
                        stringResource(R.string.product_description_example1),
                        stringResource(R.string.product_description_example2),
                        stringResource(R.string.product_description_example3),
                    ),
                ),

                AiAssistantModel(
                    image = R.drawable.ic_mail,
                    name = stringResource(R.string.email_newsletter),
                    description = stringResource(R.string.email_newsletter_description),
                    role = Constants.Business.EMAIL_NEWSLETTER + "- output should be in language ${languageNames[currentLanguageCode]}",
                    instruction = stringResource(id = R.string.email_newsletter_instruction),
                    exampleList1 = listOf(
                        stringResource(R.string.email_newsletter_example1),
                        stringResource(R.string.email_newsletter_example2),
                        stringResource(R.string.email_newsletter_example3)
                    ),
                ),

                AiAssistantModel(
                    image = R.drawable.ic_copywriting,
                    name = stringResource(R.string.facebook_adcopy),
                    description = stringResource(R.string.facebook_adcopy_description),
                    instruction = stringResource(id = R.string.facebook_adcopy_instruction),
                    role = Constants.Business.FACEBOOK_AD_COPY + "- output should be in language ${languageNames[currentLanguageCode]}",
                    exampleList1 = listOf(
                        stringResource(R.string.facebook_adcopy_example1),
                        stringResource(R.string.facebook_adcopy_example2),
                        stringResource(R.string.facebook_adcopy_example3),
                    ),
                ),

                AiAssistantModel(
                    image = R.drawable.ic_copywriting,
                    name = stringResource(R.string.google_adcopy),
                    description = stringResource(R.string.google_adcopy_description),
                    role = Constants.Business.GOOGLE_ADS_COPY + "- output should be in language ${languageNames[currentLanguageCode]}",
                    instruction = stringResource(id = R.string.google_adcopy_instruction),

                    exampleList1 = listOf(
                        stringResource(R.string.google_adcopy_example1),
                        stringResource(R.string.google_adcopy_example2),
                        stringResource(R.string.google_adcopy_example3),
                    ),
                ),

                AiAssistantModel(
                    image = R.drawable.quizmaker,
                    name = stringResource(R.string.content_ideas),
                    description = stringResource(R.string.content_ideas_description),
                    role = Constants.Business.CONTENT_IDEAS + "- output should be in language ${languageNames[currentLanguageCode]}",
                    instruction = stringResource(id = R.string.content_idea_instruction),
                    exampleList1 = listOf(
                        stringResource(R.string.content_ideas_example1),
                        stringResource(R.string.content_ideas_example2),
                    ),
                ),

                AiAssistantModel(
                    image = R.drawable.ic_landing_page,
                    name = stringResource(R.string.landingpage_copy),
                    description = stringResource(R.string.landingpage_copy_description),
                    role = Constants.Business.LANDINGPAGE_COPY + "- output should be in language ${languageNames[currentLanguageCode]}",
                    instruction = stringResource(id = R.string.landingpage_copy_instruction),
                    exampleList1 = listOf(
                        stringResource(R.string.landingpage_copy_example1),
                        stringResource(R.string.landingpage_copy_example2),
                    ),

                    ),

                AiAssistantModel(
                    image = R.drawable.ic_domains,
                    name = stringResource(R.string.domainname_serach),
                    description = stringResource(R.string.domainname_serach_description),
                    role = Constants.Business.DOMAIN_NAME_SEARCH + "- output should be in language ${languageNames[currentLanguageCode]}",
                    instruction = stringResource(id = R.string.domain_name_instruction),
                    exampleList1 = listOf(
                        stringResource(R.string.domain_name_example1),
                        stringResource(R.string.domain_name_example2),
                    ),
                ),

                AiAssistantModel(
                    image = R.drawable.ic_business_plan,
                    name = stringResource(R.string.business_plan),
                    description = stringResource(R.string.business_plan_description),
                    role = Constants.Business.BUSINESS_PLAN + "- output should be in language ${languageNames[currentLanguageCode]}",
                    instruction = stringResource(id = R.string.business_plan_instruction),
                    exampleList1 = listOf(
                        stringResource(R.string.business_plan_example1),
                        stringResource(R.string.business_plan_example2),
                    ),
                ),


                AiAssistantModel(
                    image = R.drawable.sales,
                    name = stringResource(R.string.salespitch),
                    description = stringResource(R.string.sales_desc),
                    role = Constants.Business.SALES_PITCH + "- output should be in language ${languageNames[currentLanguageCode]}",
                    instruction = stringResource(id = R.string.sales_instruction),
                    exampleList1 = listOf(
                        stringResource(R.string.sales_example),
                        stringResource(R.string.sales_example1),
                    ),
                ),

                AiAssistantModel(
                    image = R.drawable.script,
                    name = stringResource(R.string.resume_summary),
                    description = stringResource(R.string.resume_summary_desc),
                    role = Constants.Business.RESUME_SUMMARY + "- output should be in language ${languageNames[currentLanguageCode]}",
                    instruction = stringResource(id = R.string.resume_summary_instruction),
                    exampleList1 = listOf(
                        stringResource(R.string.resume_summary_example),
                        stringResource(R.string.resume_summary_example1),
                    ),
                ),

                AiAssistantModel(
                    image = R.drawable.ic_mail,
                    name = stringResource(R.string.subjectline),
                    description = stringResource(R.string.subjectline_desc),
                    role = Constants.Business.EMAIL_SUBJECT_LINE + "- output should be in language ${languageNames[currentLanguageCode]}",
                    instruction = stringResource(id = R.string.subjectline_instruction),
                    exampleList1 = listOf(
                        stringResource(R.string.subjectline_example),
                        stringResource(R.string.subjectline_example1),
                    ),
                ),

                AiAssistantModel(
                    image = R.drawable.business_name,
                    name = stringResource(R.string.bname),
                    description = stringResource(R.string.bname_desc),
                    role = Constants.Business.BUSINESS_NAME + "- output should be in language ${languageNames[currentLanguageCode]}",
                    instruction = stringResource(id = R.string.bname_instruction),
                    exampleList1 = listOf(
                        stringResource(R.string.bname_example),
                        stringResource(R.string.bname_example1),
                    ),
                ),

                AiAssistantModel(
                    image = R.drawable.tagline,
                    name = stringResource(R.string.bslogan),
                    description = stringResource(R.string.bslogan_desc),
                    role = Constants.Business.BUSINESS_SLOGAN + "- output should be in language ${languageNames[currentLanguageCode]}",
                    instruction = stringResource(id = R.string.bslogan_instruction),
                    exampleList1 = listOf(
                        stringResource(R.string.bslogan_example),
                        stringResource(R.string.bslogan_example1),
                    ),
                ),

                AiAssistantModel(
                    image = R.drawable.ic_business_plan,
                    name = stringResource(R.string.bpropsals),
                    description = stringResource(R.string.bpropsals_desc),
                    role = Constants.Business.BUSINESS_PROPOSAL + "- output should be in language ${languageNames[currentLanguageCode]}",
                    instruction = stringResource(id = R.string.bpropsals_instruction),
                    exampleList1 = listOf(
                        stringResource(R.string.bpropsals_example),
                        stringResource(R.string.bpropsals_example1),
                    ),
                ),

                )
        ),


        //Creative
        AiAssistantsModel(
            title = stringResource(R.string.creative), assistant = listOf(
                AiAssistantModel(
                    image = R.drawable.ic_song_lyrics,
                    name = stringResource(R.string.song_lyrics),
                    description = stringResource(R.string.song_lyrics_description),
                    instruction = stringResource(id = R.string.song_instruction),
                    role = Constants.Creative.SONG_LYRICS + "- output should be in language ${languageNames[currentLanguageCode]}",
                    exampleList1 = listOf(
                        stringResource(R.string.song_lyrics_example1),
                        stringResource(R.string.song_lyrics_example2),
                    ),
                ),


                AiAssistantModel(
                    image = R.drawable.script,
                    name = stringResource(R.string.midlibs),
                    description = stringResource(R.string.midlibs_desc),
                    role = Constants.Creative.MAD_LIBS_GENERATOR + "- output should be in language ${languageNames[currentLanguageCode]}",
                    instruction = stringResource(id = R.string.midlibs_instruction),
                    exampleList1 = listOf(
                        stringResource(R.string.midlibs_example),
                        stringResource(R.string.midlibs_example1),
                    ),
                ),


                AiAssistantModel(
                    image = R.drawable.emojit_to_text,
                    name = stringResource(R.string.emoji_translate),
                    description = stringResource(R.string.emoji_translate_desc),
                    role = Constants.Creative.EMOJI_TRANSLATOR + "- output should be in language ${languageNames[currentLanguageCode]}",
                    instruction = stringResource(id = R.string.emoji_translate_instruction),
                    exampleList1 = listOf(
                        stringResource(R.string.emoji_translate_example),
                        stringResource(R.string.emoji_translate_example1),
                        stringResource(R.string.emoji_translate_example2),
                        stringResource(R.string.emoji_translate_example3),
                    ),
                ),


                AiAssistantModel(
                    image = R.drawable.puzzle,
                    name = stringResource(R.string.trivia),
                    description = stringResource(R.string.trivia_desc),
                    role = Constants.Creative.TRIVIA_QUIZ_MAKER + "- output should be in language ${languageNames[currentLanguageCode]}",
                    instruction = stringResource(id = R.string.trivia_instruction),
                    exampleList1 = listOf(
                        stringResource(R.string.trivia_example),
                        stringResource(R.string.trivia_example1),
                    ),
                ),


                AiAssistantModel(
                    image = R.drawable.ic_poem,
                    name = stringResource(R.string.poem),
                    description = stringResource(R.string.poem_description),
                    role = Constants.Creative.POEMS + "- output should be in language ${languageNames[currentLanguageCode]}",
                    instruction = stringResource(id = R.string.poem_instruction),
                    exampleList1 = listOf(
                        stringResource(R.string.poem_example1),
                        stringResource(R.string.poem_example2)
                    ),
                ),


                AiAssistantModel(
                    image = R.drawable.quizmaker,
                    name = stringResource(R.string.jingle),
                    description = stringResource(R.string.jingle_desc),
                    role = Constants.Creative.JINGLE_WRITER + "- output should be in language ${languageNames[currentLanguageCode]}",
                    instruction = stringResource(id = R.string.jingle_instruction),
                    exampleList1 = listOf(
                        stringResource(R.string.jingle_example),
                        stringResource(R.string.jingle_example1),
                    ),
                ),


                AiAssistantModel(
                    image = R.drawable.movie_idea,
                    name = stringResource(R.string.movie_mood),
                    description = stringResource(R.string.movie_mood_desc),
                    instruction = stringResource(id = R.string.movie_mood_instruction),
                    role = Constants.Creative.MOVIE_BY_MOOD + "- output should be in language ${languageNames[currentLanguageCode]}",
                    exampleList1 = listOf(
                        stringResource(R.string.movie_mood_example),
                        stringResource(R.string.movie_mood_example1),
                    ),
                ),


                AiAssistantModel(
                    image = R.drawable.person,
                    name = stringResource(R.string.paradoy),
                    description = stringResource(R.string.paradoy_desc),
                    role = Constants.Creative.CELEBRITY_PARAODY + "- output should be in language ${languageNames[currentLanguageCode]}",
                    instruction = stringResource(id = R.string.paradoy_instruction),
                    exampleList1 = listOf(
                        stringResource(R.string.parody_example),
                        stringResource(R.string.parody_example1),
                        stringResource(R.string.parody_example2),
                    ),
                ),


                AiAssistantModel(
                    image = R.drawable.code,
                    name = stringResource(R.string.code_poem),
                    description = stringResource(R.string.code_poem_desc),
                    role = Constants.Creative.CODE_POEM + "- output should be in language ${languageNames[currentLanguageCode]}",
                    instruction = stringResource(id = R.string.code_poem_instruction),
                    exampleList1 = listOf(
                        stringResource(R.string.code_poem_example),
                        stringResource(R.string.code_poem_example1),
                    ),
                ),

                AiAssistantModel(
                    image = R.drawable.ttwister,
                    name = stringResource(R.string.tongue_twister),
                    description = stringResource(R.string.tongue_twister_desc),
                    role = Constants.Creative.TONGUE_TWISTER + "- output should be in language ${languageNames[currentLanguageCode]}",
                    instruction = stringResource(id = R.string.tongue_twister_instruction),
                    exampleList1 = listOf(
                        stringResource(R.string.tongue_twister_example),
                        stringResource(R.string.tongue_twister_example1),
                    ),
                ),

//            AiAssistantModel(
//                image = R.drawable.puzzle,
//                name = stringResource(R.string.riddle),
//                description = stringResource(R.string.riddle_desc),
//                role = Constants.Creative.RIDDLE_WRITER,
//                instruction= stringResource(id = R.string.riddle_instruction),
//                exampleList1 = listOf(
//                    stringResource(R.string.riddle_example),
//                    stringResource(R.string.riddle_example1),
//                ),
//            ),

                AiAssistantModel(
                    image = R.drawable.ic_poem,
                    name = stringResource(R.string.haiku),
                    description = stringResource(R.string.haiku_desc),
                    role = Constants.Creative.HAIKU_GENERATOR + "- output should be in language ${languageNames[currentLanguageCode]}",
                    instruction = stringResource(id = R.string.haiku_instruction),
                    exampleList1 = listOf(
                        stringResource(R.string.haiku_example),
                        stringResource(R.string.haiku_example1),
                    ),
                ),

                AiAssistantModel(
                    image = R.drawable.script,
                    name = stringResource(R.string.short_story),
                    description = stringResource(R.string.short_story_description),
                    role = Constants.Creative.SHORT_STORY + "- output should be in language ${languageNames[currentLanguageCode]}",
                    instruction = stringResource(id = R.string.short_story_instruction),
                    exampleList1 = listOf(
                        stringResource(R.string.short_story_example1),
                        stringResource(R.string.short_story_example2),
                    ),
                ),
            )
        ),

        //
        AiAssistantsModel(
            title = stringResource(R.string.health), assistant = listOf(
                AiAssistantModel(
                    image = R.drawable.ic_recipe,
                    name = stringResource(R.string.nutrition),
                    description = stringResource(R.string.nutrition_desc),
                    role = Constants.Health.NUTRITION_COACH + "- output should be in language ${languageNames[currentLanguageCode]}",
                    instruction = stringResource(id = R.string.nutrition_instruction),
                    exampleList1 = listOf(
                        stringResource(R.string.nutrition_example),
                        stringResource(R.string.nutrition_example1),
                    )
                ),

                AiAssistantModel(
                    image = R.drawable.fitness,
                    name = stringResource(R.string.fitness),
                    description = stringResource(R.string.fitness_desc),
                    role = Constants.Health.FITNESS_COACH + "- output should be in language ${languageNames[currentLanguageCode]}",
                    instruction = stringResource(id = R.string.fitness_instruction),
                    exampleList1 = listOf(
                        stringResource(R.string.fitness_example),
                        stringResource(R.string.fitness_example1),
                    ),
                ),

                AiAssistantModel(
                    image = R.drawable.meditation,
                    name = stringResource(R.string.meditaion),
                    description = stringResource(R.string.meditaion_desc),
                    instruction = stringResource(id = R.string.meditaion_instruction),
                    role = Constants.Health.MEDIATION_COACH + "- output should be in language ${languageNames[currentLanguageCode]}",
                    exampleList1 = listOf(
                        stringResource(R.string.meditaion_example),
                        stringResource(R.string.meditaion_example1),
                    ),
                ),

                AiAssistantModel(
                    image = R.drawable.yoga,
                    name = stringResource(R.string.yoga),
                    description = stringResource(R.string.yoga_desc),
                    role = Constants.Health.YOGA_COACH + "- output should be in language ${languageNames[currentLanguageCode]}",
                    instruction = stringResource(id = R.string.yoga_instruction),
                    exampleList1 = listOf(
                        stringResource(R.string.yoga_example),
                        stringResource(R.string.yoga_example1),
                    ),
                ),
            )
        )
    )



//    LaunchedEffect(Unit) {
//        viewModel.getCurrentLanguageCode()
//        val currentLanguageCode = viewModel.currentLanguageCode.value
//        changeLanguage(context, currentLanguageCode)
//    }


    Column(Modifier)
    {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Column(Modifier.fillMaxSize()) {
                MainAppBar(
                    onClickAction = {},
                    image = R.drawable.app_icon,
                    text = stringResource(R.string.my_assistants),
                    tint = Color.Unspecified
                )
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 2.dp, start = 10.dp)
                )
                {
                    item {
                        TabItem(
                            text = stringResource(R.string.all),
                            selected = viewModel.selectedValue.value == "",
                            onClick = {
                                viewModel.selectedValue.value = ""
                                viewModel.showVertical.value = false
                            }
                        )
                    }

                    items(assistantList){
                        TabItem(
                            text = it.title,
                            selected = viewModel.selectedValue.value == it.title,
                            onClick = {
                                viewModel.selectedValue.value = it.title
                                viewModel.verticalShowList.clear()
                                viewModel.verticalShowList.addAll(it.assistant)
                                viewModel.showVertical.value = true
                            }
                        )
                    }
                }

                if (viewModel.showVertical.value) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, end = 10.dp, top = 12.dp)
                    ) {
                        items(viewModel.verticalShowList)
                        {
                            AssistantCard(
                                image = it.image,
                                name = it.name,
                                description = it.description,
                                onClick =
                                {
                                    if (isClickEnabled) {
                                        isClickEnabled = false
                                        coroutineScope.launch {
                                            delay(450)
                                            isClickEnabled = true
                                        }
                                        if(Constants.isInterEnabled){
                                            showInterstitial(context,
                                                onAdDismissed = {
                                                    viewModel.navigateToChat {
                                                        navigateToChat(
                                                            it.name,
                                                            it.role,
                                                            it.instruction,
                                                            it.exampleList1
                                                        )
                                                    }
                                                },
                                                onAdFailed = {
                                                    viewModel.navigateToChat {
                                                        navigateToChat(
                                                            it.name,
                                                            it.role,
                                                            it.instruction,
                                                            it.exampleList1
                                                        )
                                                    }
                                                }
                                            )
                                        }else {
                                            viewModel.navigateToChat {
                                                navigateToChat(
                                                    it.name,
                                                    it.role,
                                                    it.instruction,
                                                    it.exampleList1
                                                )
                                            }
                                        }
                                    }
                                }
                            )
                        }
                    }
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, end = 10.dp, top = 12.dp)
                    ) {
                        assistantList.forEach { aiAssistantList ->
                            aiAssistantList.assistant.forEach { assistant ->
                                item {
                                    AssistantCard(
                                        image = assistant.image,
                                        name = assistant.name,
                                        description = assistant.description,
                                        onClick =
                                        {
                                            if (isClickEnabled) {
                                                isClickEnabled = false
                                                coroutineScope.launch {
                                                    delay(450)
                                                    isClickEnabled = true
                                                }
                                                if(Constants.isInterEnabled){
                                                    showInterstitial(context,
                                                        onAdDismissed = {
                                                            viewModel.navigateToChat {
                                                                navigateToChat(
                                                                    assistant.name,
                                                                    assistant.role,
                                                                    assistant.instruction,
                                                                    assistant.exampleList1
                                                                )
                                                            }
                                                        },

                                                        onAdFailed = {
                                                            navigateToChat(
                                                                assistant.name,
                                                                assistant.role,
                                                                assistant.instruction,
                                                                assistant.exampleList1
                                                            )
                                                        }
                                                    )
                                                }else{
                                                    viewModel.navigateToChat {
                                                        navigateToChat(
                                                            assistant.name,
                                                            assistant.role,
                                                            assistant.instruction,
                                                            assistant.exampleList1
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


////    var showDialog by remember {
////        mutableStateOf(false)
////    }
////     LaunchedEffect(Unit){
////     if (isOnline(context).not()) {
////         showDialog = true
////     }
////     }
////    if (isOnline(context).not()) {
////        showDialog = true
////    }
////    if (showDialog) {
////        NoInternetDialog {
////            showDialog = false
////        }
////    }

