<template>
    <article class="mb-3 py-4 border-bottom">
        <header>
            <h4>
                <slot name="question" />
                <FgpSiteLink :path="`${$config.public.paths.faqs}#app`" class="small text-info">&uparrow;</FgpSiteLink>
            </h4>
        </header>
        <section class="px-3">
            <slot name="answer" />
        </section>
    </article>
</template>
