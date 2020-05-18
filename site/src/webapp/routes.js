import FgpConfiguration from "./components/configuration.vue";
import FgpHome from "./components/home.vue";
import FgpInstallation from "./components/installation.vue";
import FgpTasks from "./components/tasks.vue";
import FgpNotFound from "./components/not-found.vue";

const FGP_ROUTES = [
    {
        path: "/",
        component: FgpHome
    },
    {
        path: "/configuration",
        component: FgpConfiguration
    },
    {
        path: "/installation",
        component: FgpInstallation
    },
    {
        path: "/tasks",
        component: FgpTasks
    },
    {
        path: "*",
        component: FgpNotFound
    }
];

export default FGP_ROUTES;
