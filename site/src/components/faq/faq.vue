<template>
    <article class="mt-4 pb-2 border-bottom">
        <header>
            <h4>
                <slot name="question" />
                <FgpSiteLink path="#app" class="small text-info">&uparrow;</FgpSiteLink>
            </h4>
        </header>
        <section class="px-3">
            <slot name="answer" />
        </section>
    </article>
</template>
