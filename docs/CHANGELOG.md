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

## [v0.1.1-alpha] - 2026-10-19
### Añadido
- **Controlador de Cámara Cinemática Diegética (`DiegeticCameraController`)**:
  - Estado cinemático con interpolación cúbica `easeInOutCubic` de 0.0 a 1.0 al entrar y salir del workbench.
  - Cámara sobre el hombro enfocada en el tapete de trabajo con pitch ~38° y yaw anclado al `FACING` del bloque.
  - Parallax de cabeza dinámico por ratón con restricción de cono elástica `[-14.0°, 14.0°]` y recuperación elástica suave.
  - Mixin invoker `CameraAccessor` para invocación segura de `setPosition` y `setRotation`.
- **Pantalla Táctica Diegética Interactiva (`WorkbenchScreen`)**:
  - `renderBackground` transparente para vista limpia y sin oscurecimiento del mundo 3D circundante.
  - Marco reticular táctico con esquinas reforzadas y estilos de interfaz militar.
  - 4 cajas de ranuras interactivas (`SLOT_PRIMARY`, `SLOT_OPTIC`, `SLOT_BARREL`, `SLOT_UNDERBARREL`) con soporte para equipar/desequipar con clic del ratón y tooltips informativos.
  - Barra de condición y limpieza del arma en tiempo real con transiciones de color dinámicas (verde > 70%, amarillo > 30%, rojo <= 30%) y monitor de desgaste.
  - Botones tácticos foley: "Ciclar Cerrojo" (partículas y sonido mecánico), "Limpiar y Aceitar" (aumenta condición con sonido y partículas) y "Ajustar Tornillos" (reduce desgaste con sonido).
- **Renderizado Físico 3D en Mesa (`TacticalWorkbenchRenderer`)**:
  - `BlockEntityRenderer<TacticalWorkbenchBlockEntity>` que posiciona el arma horizontalmente en el centro del tapete.
  - Renderizado socketizado de accesorios montados (mira réflex en el riel superior, silenciador en la boca de fuego y empuñadura en el riel inferior).
  - Rotación física precisa sincronizada con la orientación horizontal (`FACING`) del bloque.
- **Integración y Mixins Multi-Plataforma**:
  - `CameraMixin` en Fabric inyectando en `Camera.setup(..., @At("TAIL"))` para transformar la cámara suavemente.
  - `ForgeClientEvents` suscrito a `ViewportEvent.ComputeCameraAngles` para sincronizar ángulos en Forge.
  - Inicializadores de cliente `TacticalWorkbenchFabricClient` y registro `EntityRenderersEvent.RegisterRenderers` en Forge.

## [v0.1.2-alpha] - 2026-10-19
### Corregido
- **Aislamiento estricto de paquetes Mixin**:
  - Reubicado `CameraAccessor` al subpaquete `dev.itsrealperson.tacticalworkbench.mixin`.
  - Configurado `tactical_workbench-common.mixins.json` con `"package": "dev.itsrealperson.tacticalworkbench.mixin"`.
  - Configurado `tactical_workbench-fabric.mixins.json` con `"package": "dev.itsrealperson.tacticalworkbench.fabric.client.mixin"`.
  - Configurado `tactical_workbench-forge.mixins.json` con `"package": "dev.itsrealperson.tacticalworkbench.forge.mixin"`.
  - Solucionado el error fatal `IllegalClassLoadError` en Fabric Loader 0.20.0 que impedía arrancar el mod por colisión de paquetes de mixin con el `ModInitializer`.

