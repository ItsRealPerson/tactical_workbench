# Plan v1: Réplica del Workbench Táctico y Diegético (Minecraft 1.20.1 Forge & Fabric)

## 1. Visión del Proyecto
Crear una réplica fiel y funcional para **Minecraft 1.20.1** (soportando tanto **Forge** como **Fabric** vía **Architectury Loom**) del sistema de **Workbench Táctico** visto en *Rot n' Remnants* (por Grimm), inspirado a su vez en las estaciones de trabajo inmersivas de *The Last of Us*, *Metro Exodus* y *Escape from Tarkov*.

## 2. Objetivos Principales
1. **Banco de Trabajo Táctico (`TacticalWorkbenchBlock` + `BlockEntity`)**:
   - Bloque orientable (North, South, East, West) con tapete de trabajo, lámpara y herramientas.
   - Capacidad de sostener un arma o herramienta principal y acoples/materiales de mantenimiento.
   - Sincronización de inventario cliente-servidor en tiempo real.
2. **Entidad de Asiento Diegético (`SeatEntity`)**:
   - Al interactuar con el banco, el jugador se sienta suavemente frente a la mesa para anclar su posición corporal.
3. **Controlador de Cámara Cinemática (`DiegeticCameraController`)**:
   - Transición fluida con interpolación Bézier (`easeInOutCubic`) al entrar y salir del banco.
   - Punto de vista enfocado en el tapete de trabajo desde un ángulo cenital/sobre el hombro.
   - *Head Parallax*: sutil balanceo de la cabeza al mover el ratón con restricción de cono elástica.
4. **Pantalla Diegética (`WorkbenchScreen`)**:
   - Anulación del oscurecimiento 2D de Minecraft (`renderBackground` transparente).
   - Menú contextual táctico: slots de accesorios (mira, cañón, empuñadura), estado de desgaste/suciedad, y acciones de ensamblaje / mantenimiento.
5. **Renderizado 3D en Mesa (`WorkbenchBlockEntityRenderer`)**:
   - Renderizado físico del arma reposando en la mesa con soporte para accesorios acoplados mediante traslaciones y rotaciones por sockets.
6. **Diseño Sonoro Foley Táctil**:
   - Sonidos personalizados de clics mecánicos, tornillos, roscas, cerrojos y engrase.

## 3. Arquitectura del Código
- **`common/`**:
  - `TacticalWorkbench`: Clase principal del mod y registro diferido de bloques, ítems, entidades y sonidos.
  - `block/TacticalWorkbenchBlock`: Bloque con facing e interacción.
  - `block/entity/TacticalWorkbenchBlockEntity`: Lógica de almacenamiento NBT y sincronización.
  - `entity/WorkbenchSeatEntity`: Entidad invisible para montar al jugador.
  - `item/TacticalRifleItem`, `TacticalAttachmentItem`, `GunOilItem`: Ítems tácticos de prueba.
  - `client/camera/DiegeticCameraController`: Lógica matemática de cámara e interpolación.
  - `client/screen/WorkbenchScreen`: GUI inmersiva sin fondo oscuro.
  - `client/render/TacticalWorkbenchRenderer`: Renderizado de ítems y accesorios en el mundo 3D.
- **`forge/`**:
  - `TacticalWorkbenchForge`: Entrada de Forge, buses de eventos y `ViewportEvent.ComputeCameraAngles` para la cámara.
- **`fabric/`**:
  - `TacticalWorkbenchFabric`: Entrada de Fabric.
  - `client/mixin/CameraMixin`: Inyección de cámara cinemática en Fabric.

## 4. Fases de Ejecución
- **Fase 1**: Configuración de Gradle multi-loader (root, common, forge, fabric) y repositorios.
- **Fase 2**: Implementación del núcleo `common` (bloque, entidad de bloque, asiento, ítems tácticos y sonidos).
- **Fase 3**: Implementación del cliente diegético (cámara cinemática, screen transparente y renderer 3D).
- **Fase 4**: Conexión de plataformas específicas (Forge y Fabric) y assets (modelos JSON, texturas, blockstates y sonidos).
- **Fase 5**: Compilación de verificación y commit/push a GitHub.
