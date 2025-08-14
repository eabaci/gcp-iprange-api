package ea.home.gcp.common

enum class Region(val scopes: List<String>) {
    EU(
        listOf(
            "europe-west1", "europe-west2", "europe-west3", "europe-west4", "europe-west6",
            "europe-west8", "europe-west9", "europe-west10", "europe-west12", "europe-central2"
        )
    ),
    US(
        listOf(
            "us-central1", "us-central2", "us-east1", "us-east4", "us-east5", "us-south1",
            "us-west1", "us-west2", "us-west3", "us-west4"
        )
    ),
    ME(listOf("me-west1", "me-central1", "me-central2")),
    NA(listOf("northamerica-northeast1", "northamerica-northeast2", "northamerica-south1")),
    SA(listOf("southamerica-east1", "southamerica-west1")),
    AS(
        listOf(
            "asia-east1", "asia-east2", "asia-northeast1", "asia-northeast2",
            "asia-northeast3", "asia-south1", "asia-south2", "asia-southeast1", "asia-southeast2"
        )
    ),
    AF(listOf("africa-south1", "africa-north1")),
    AUS(listOf("australia-southeast1", "australia-southeast2")),
    GLOBAL(listOf("global")),
    ALL(emptyList())
}
