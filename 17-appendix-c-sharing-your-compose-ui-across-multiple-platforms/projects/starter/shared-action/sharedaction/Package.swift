// swift-tools-version:5.3
import PackageDescription

let package = Package(
    name: "SharedAction",
    platforms: [
        .iOS(.v13)
    ],
    products: [
        .library(
            name: "SharedAction",
            targets: ["SharedAction"]
        ),
    ],
    targets: [
        .binaryTarget(
            name: "SharedAction",
            path: "./SharedAction.xcframework"
        ),
    ]
)
