project.ext.set("nodeInstallDirectory", "${project.rootDir}/node")

tasks.named<Wrapper>("wrapper") {
    distributionType = Wrapper.DistributionType.ALL
}
