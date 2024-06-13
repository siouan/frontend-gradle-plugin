<template>
    <FgpFaq>
        <template #question>
            How can I provide additional environment variables to <FgpCode>node</FgpCode>/<FgpCode
            >corepack</FgpCode>/<FgpCode>npm</FgpCode>/<FgpCode>pnpm</FgpCode>/<FgpCode>yarn</FgpCode> processes?
        </template>
        <template #answer>
            <p>
                See <FgpSiteLink :path="`${$config.public.paths.tasks}#custom-environment-variables`"
                >this section</FgpSiteLink>.
            </p>
        </template>
    </FgpFaq>
</template>
