# Changelog

Todas las modificaciones notables del proyecto se documentan en este archivo de manera cronológica e incremental.

## [v0.1.0-alpha] - 2026-10-18
### Añadido
- Inicialización del proyecto `tactical_workbench` con soporte multi-cargador (Architectury Loom para Minecraft 1.20.1 Forge y Fabric).
- Infraestructura de optimización universal: `.agyignore`, `AGENTS.md`, `.agent/workflows/arquitectura.md` y `.agent/workflows/ejecutar-feature.md`.
- Documento de planificación inicial `docs/planes/plan_v1.md`.
- Wrapper de Gradle y configuración de compilación multiplataforma.
- Implementación de la lógica del núcleo (`common`):
  - Clase principal `TacticalWorkbench` con `DeferredRegisters` de Architectury (`BLOCK`, `BLOCK_ENTITY_TYPE`, `ITEM`, `ENTITY_TYPE`, `SOUND_EVENT`).
  - Registro de bloques y entidad de bloque: `TacticalWorkbenchBlock` y `TacticalWorkbenchBlockEntity` con inventario para 4 ranuras (arma principal, óptica, cañón, bajo cañón) y atributos de desgaste y limpieza.
  - Registro de ítems tácticos: `TacticalWorkbenchItem`, `TacticalRifleItem`, accesorios tácticos (`ReflexSight`, `Suppressor`, `VerticalGrip`) y `GunOilItem` con tooltips detallados.
  - Entidad invisible de asiento `WorkbenchSeatEntity` para posicionamiento corporal diegético y auto-descarte al desmontar o destruir la mesa.
  - Foley sonoro completo en `ModSounds`: `seat_sit`, `seat_stand`, `attach_equip`, `attach_remove`, `bolt_cycle`, `screw_turn`, `oil_wipe`.
  - Pantalla diegética inicial `WorkbenchScreen` y aislador de cliente `TacticalWorkbenchClientHelper`.
  - Archivos de internacionalización en inglés (`en_us.json`) y español (`es_es.json`).
