package ea.home.gcp.common

enum class Region(val scopes: List<String>) {
    EU(listOf("europe-west1", "europe-west2", "europe-west3", "europe-west4", "europe-west6")),
    US(listOf("us-central1", "us-east1", "us-east4", "us-west1", "us-west2")),
    ME(listOf("me-west1")),
    NA(listOf("northamerica-northeast1")),
    SA(listOf("southamerica-east1")),
    AS(listOf("asia-east1", "asia-east2", "asia-northeast1", "asia-northeast2", "asia-south1", "asia-southeast1", "asia-southeast2")),
    AF(listOf("africa-south1")),
    AUS(listOf("australia-southeast1")),
    ALL(emptyList())
}
