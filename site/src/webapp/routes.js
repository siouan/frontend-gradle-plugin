import fgpConfiguration from "./component/configuration.vue";
import fgpFaqs from "./component/faqs.vue";
import fgpInstallation from "./component/installation.vue";
import fgpNotFound from "./component/not-found.vue";
import fgpOverview from "./component/overview.vue";
import fgpPaths from "./paths";
import fgpTasks from "./component/tasks.vue";

const FGP_ROUTES = [
    {
        path: fgpPaths.configuration,
        component: fgpConfiguration
    },
    {
        path: fgpPaths.faqs,
        component: fgpFaqs
    },
    {
        path: fgpPaths.installation,
        component: fgpInstallation
    },
    {
        path: fgpPaths.overview,
        component: fgpOverview
    },
    {
        path: fgpPaths.tasks,
        component: fgpTasks
    },
    {
        path: "*",
        component: fgpNotFound
    }
];

export default FGP_ROUTES;
