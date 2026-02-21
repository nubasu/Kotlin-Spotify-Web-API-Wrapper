---
name: spotify-kmp-api-maintainer
description: Maintain and extend a Kotlin Multiplatform wrapper for Spotify Web API using Ktor client and kotlinx.serialization. Use when adding or updating Spotify endpoints, request or response models, auth handling, query or path mapping, or fixing compile/runtime issues in shared/commonMain.
---

# Goal
Implement or modify Spotify Web API features in this repository with minimal regression.

## Repository Focus
- Edit API classes under `shared/src/commonMain/kotlin/.../api/*`
- Edit request models under `shared/src/commonMain/kotlin/.../request/*`
- Edit response models under `shared/src/commonMain/kotlin/.../response/*`
- Keep compatibility across KMP targets by treating `commonMain` as the source of truth

## Workflow
1. Read the target endpoint spec from Spotify Web API docs and list required `path`, `query`, `body`, and response fields.
2. Find the nearest existing implementation pattern in `api/*` (for example `AlbumsApis.kt`, `PlayerApis.kt`) and follow it.
3. Add or update request and response models in the correct domain package.
4. Implement or update `suspend` API methods using Ktor request builders, bearer auth, and explicit error handling.
5. Verify mapping for optional parameters and default values to avoid silent behavior drift.
6. Update tests under `shared/src/commonTest/kotlin` when practical, or at minimum keep compile safety.
7. Run module compile/test tasks and resolve failures before finishing.

## Guardrails
- Preserve package structure and naming conventions by domain.
- Avoid platform-specific APIs in `commonMain`.
- Reuse shared request/value objects when available (`PagingOptions`, `Ids`, `CountryCode`, etc.).
- Keep public API changes explicit and scoped to the requested feature.
- Do not bundle unrelated refactors in the same change.

## Done Criteria
- Endpoint behavior matches Spotify docs for required and optional params.
- `shared` module compiles cleanly.
- Updated model types align with JSON payload shape.
- Error paths return actionable messages.
