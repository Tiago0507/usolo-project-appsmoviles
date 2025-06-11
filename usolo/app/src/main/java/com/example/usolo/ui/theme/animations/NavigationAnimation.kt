import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.navigation.NavBackStackEntry

/**
 * Clase utilitaria que contiene todas las animaciones de navegación
 * para mantener consistencia y facilitar el mantenimiento
 */
object NavigationAnimations {


    object Duration {
        const val STANDARD = 400
        const val FAST = 250
        const val SLOW = 600
        const val INSTANT = 150
    }


    object Easing {
        val standard = CubicBezierEasing(0.4f, 0.0f, 0.2f, 1f)
        val emphasized = CubicBezierEasing(0.2f, 0.0f, 0f, 1f)
        val decelerate = CubicBezierEasing(0.0f, 0.0f, 0.2f, 1f)
        val accelerate = CubicBezierEasing(0.4f, 0f, 1f, 1f)
    }

    object Scale {
        const val SUBTLE = 0.95f
        const val MEDIUM = 0.85f
        const val DRAMATIC = 0.7f
    }

    fun slideFromBottom(
        duration: Int = Duration.STANDARD,
        easing: CubicBezierEasing = Easing.emphasized
    ): AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition {
        return {
            slideInVertically(
                animationSpec = tween(duration, easing = easing),
                initialOffsetY = { it }
            ) + fadeIn(
                animationSpec = tween(duration, easing = Easing.standard)
            )
        }
    }


    fun slideFromRight(
        duration: Int = Duration.STANDARD,
        easing: CubicBezierEasing = Easing.emphasized
    ): AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition {
        return {
            slideInHorizontally(
                animationSpec = tween(duration, easing = easing),
                initialOffsetX = { it }
            ) + fadeIn(
                animationSpec = tween(Duration.FAST, easing = Easing.standard)
            )
        }
    }


    fun slideFromLeft(
        duration: Int = Duration.STANDARD,
        easing: CubicBezierEasing = Easing.emphasized
    ): AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition {
        return {
            slideInHorizontally(
                animationSpec = tween(duration, easing = easing),
                initialOffsetX = { -it }
            ) + fadeIn(
                animationSpec = tween(Duration.FAST, easing = Easing.standard)
            )
        }
    }


    fun slideFromTop(
        duration: Int = Duration.STANDARD,
        easing: CubicBezierEasing = Easing.emphasized
    ): AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition {
        return {
            slideInVertically(
                animationSpec = tween(duration, easing = easing),
                initialOffsetY = { -it }
            ) + fadeIn(
                animationSpec = tween(Duration.FAST, easing = Easing.standard)
            )
        }
    }


    fun fadeIn(
        duration: Int = Duration.FAST,
        easing: CubicBezierEasing = Easing.standard
    ): (AnimatedContentTransitionScope<NavBackStackEntry>) -> EnterTransition {
        return {
            fadeIn(animationSpec = tween(duration, easing = easing))
        }
    }


    fun scaleIn(
        duration: Int = Duration.STANDARD,
        initialScale: Float = Scale.MEDIUM,
        easing: CubicBezierEasing = Easing.emphasized
    ): AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition {
        return {
            scaleIn(
                animationSpec = tween(duration, easing = easing),
                initialScale = initialScale
            ) + fadeIn(
                animationSpec = tween(Duration.FAST, easing = Easing.standard)
            )
        }
    }

    fun sharedElementEnter(
        duration: Int = Duration.STANDARD,
        scale: Float = Scale.SUBTLE
    ): AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition {
        return {
            slideInVertically(
                animationSpec = tween(duration, easing = Easing.emphasized),
                initialOffsetY = { it / 3 }
            ) + fadeIn(
                animationSpec = tween(duration, easing = Easing.standard)
            ) + scaleIn(
                animationSpec = tween(duration, easing = Easing.emphasized),
                initialScale = scale
            )
        }
    }

    fun slideToTop(
        duration: Int = Duration.STANDARD,
        easing: CubicBezierEasing = Easing.emphasized
    ): AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition {
        return {
            slideOutVertically(
                animationSpec = tween(duration, easing = easing),
                targetOffsetY = { -it }
            ) + fadeOut(
                animationSpec = tween(duration, easing = Easing.standard)
            )
        }
    }

    fun slideToLeft(
        duration: Int = Duration.STANDARD,
        easing: CubicBezierEasing = Easing.emphasized
    ): AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition {
        return {
            slideOutHorizontally(
                animationSpec = tween(duration, easing = easing),
                targetOffsetX = { -it }
            ) + fadeOut(
                animationSpec = tween(Duration.FAST, easing = Easing.standard)
            )
        }
    }

    fun slideToRight(
        duration: Int = Duration.STANDARD,
        easing: CubicBezierEasing = Easing.emphasized
    ): AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition {
        return {
            slideOutHorizontally(
                animationSpec = tween(duration, easing = easing),
                targetOffsetX = { it }
            ) + fadeOut(
                animationSpec = tween(Duration.FAST, easing = Easing.standard)
            )
        }
    }

    fun slideToBottom(
        duration: Int = Duration.STANDARD,
        easing: CubicBezierEasing = Easing.emphasized
    ): AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition {
        return {
            slideOutVertically(
                animationSpec = tween(duration, easing = easing),
                targetOffsetY = { it }
            ) + fadeOut(
                animationSpec = tween(Duration.FAST, easing = Easing.standard)
            )
        }
    }

    fun fadeOut(
        duration: Int = Duration.FAST,
        easing: CubicBezierEasing = Easing.standard
    ): AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition {
        return {
            fadeOut(animationSpec = tween(duration, easing = easing))
        }
    }

    fun scaleOut(
        duration: Int = Duration.STANDARD,
        targetScale: Float = Scale.MEDIUM,
        easing: CubicBezierEasing = Easing.emphasized
    ): AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition {
        return {
            scaleOut(
                animationSpec = tween(duration, easing = easing),
                targetScale = targetScale
            ) + fadeOut(
                animationSpec = tween(Duration.FAST, easing = Easing.standard)
            )
        }
    }

    fun sharedElementExit(
        duration: Int = Duration.STANDARD,
        scale: Float = Scale.SUBTLE
    ): AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition {
        return {
            slideOutVertically(
                animationSpec = tween(duration, easing = Easing.emphasized),
                targetOffsetY = { -it / 3 }
            ) + fadeOut(
                animationSpec = tween(duration, easing = Easing.standard)
            ) + scaleOut(
                animationSpec = tween(duration, easing = Easing.emphasized),
                targetScale = scale
            )
        }
    }


    object Combinations {

        val authFlow = AnimationPair(
            enter = slideFromBottom(),
            exit = slideToTop()
        )


        val sideNavigation = AnimationPair(
            enter = slideFromRight(),
            exit = slideToLeft()
        )


        val detailScreen = AnimationPair(
            enter = sharedElementEnter(),
            exit = sharedElementExit()
        )


        val modal = AnimationPair(
            enter = scaleIn(),
            exit = scaleOut()
        )


        val subtle = AnimationPair(
            enter = fadeIn(),
            exit = fadeOut()
        )


        val mainScreen = AnimationPair(
            enter = sharedElementEnter(duration = Duration.SLOW),
            exit = sharedElementExit(duration = Duration.SLOW)
        )
    }


    data class AnimationPair(
        val enter: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition,
        val exit: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition
    )

    /**
     * Builder para crear animaciones personalizadas fácilmente
     */
    class AnimationBuilder {
        private var enterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition)? = null
        private var exitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition)? = null

        fun enter(transition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition) = apply {
            enterTransition = transition
        }

        fun exit(transition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition) = apply {
            exitTransition = transition
        }

        fun build() = AnimationPair(
            enter = enterTransition ?: fadeIn(),
            exit = exitTransition ?: fadeOut()
        )
    }


    fun custom(builder: AnimationBuilder.() -> Unit): AnimationPair {
        return AnimationBuilder().apply(builder).build()
    }
}