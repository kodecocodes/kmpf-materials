// swift-tools-version:5.3
import PackageDescription

let package = Package(
    name: "SharedLogger",
    platforms: [
        .iOS(.v13)
    ],
    products: [
        .library(
            name: "SharedLogger",
            targets: ["SharedLogger"]
        ),
    ],
    targets: [
        .binaryTarget(
            name: "SharedLogger",
            path: "./SharedLogger.xcframework"
        ),
    ]
)
