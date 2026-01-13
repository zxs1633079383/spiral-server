# SpiralServer v1

Enterprise-grade, schema-driven, deterministic Agent Runtime.

## Project Structure

Multi-module Gradle project with clear separation of concerns:

### Core Modules

- **schema/** - Schema definitions, versioning, validation
- **state-plane/** - State management abstraction (hot state, event log, snapshots)
- **runtime/** - Deterministic execution engine (planner, executor, saga, replay)
- **control-plane/** - Agent orchestration (registry, event router, scheduler)

### Supporting Modules

- **governance/** - Policy enforcement and audit
- **observability/** - Tracing, metrics, audit models
- **api/** - External interfaces (gRPC + HTTP)
- **tools/** - Tool SPI and adapters
- **deploy/** - Deployment configurations
- **examples/** - Example agents and usage patterns

## Module Dependencies

```
schema (foundation)
  ↑
  ├── state-plane
  ├── observability
  ├── governance
  │     ↑
  │     └── control-plane
  │           ↑
  │           ├── runtime (depends on state-plane)
  │           └── api
  │                 ↑
  │                 └── deploy
  │
  └── tools
        ↑
        └── examples
```

## Build

```bash
./gradlew build
```

## Design Principles

1. **Determinism** - Runtime cannot access IO directly; all side-effects via events
2. **Plane Separation** - Control Plane / Runtime / State Plane are independent
3. **Governance** - Enforced, not advisory; all decisions auditable
4. **Java Middleware Quality** - Strong typing, interfaces > implementations, SPI-based extension

## Status

**Phase 1 - Bootstrap**: Module structure and core abstractions (IN PROGRESS)
