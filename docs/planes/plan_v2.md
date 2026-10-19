# Plan v2: Implementación de Sistemas de Cliente y Cámara Cinemática Diegética

## 1. Contexto y Objetivos
En esta fase (Fase 3 del proyecto Tactical Workbench para Minecraft 1.20.1 Architectury Forge & Fabric), se implementan la cámara diegética cinemática, la pantalla táctica transparente con interacción foley e inspectora de arma, el renderizador 3D físico sobre el banco de trabajo y la integración multiplataforma (Mixins para Fabric y Eventos para Forge).

## 2. Componentes a Desarrollar

### 2.1. `DiegeticCameraController` (`common/.../client/camera/DiegeticCameraController.java`)
- Gestiona el estado y animación matemática de la cámara diegética cuando el jugador abre la mesa de trabajo o se sienta en ella.
- Variables:
  - `isActive`: booleano que indica si la cámara cinemática está activa.
  - `progress`: float de `0.0f` a `1.0f` interpolado cúbicamente (`easeInOutCubic`).
  - `workbenchPos`: `BlockPos` del banco activo.
  - `facing`: `Direction` del banco.
  - `parallaxYaw`, `parallaxPitch`: acumuladores de rotación por ratón limitados a `[-14.0f, 14.0f]` con recuperación elástica / resorte suave hacia `0`.
- Métodos:
  - `start(BlockPos pos, Direction facing)`
  - `stop()`
  - `update(float partialTicks)`
  - `applyCameraTransform(Camera camera, float partialTicks)`: calcula la posición 3D deseada (ligeramente detrás del jugador mirando al banco con pitch ~38° y yaw según facing) e interpola suavemente desde la cámara de Minecraft.
  - `onMouseTurn(double dx, double dy)`: añade deltas al parallax con amortiguación.

### 2.2. `WorkbenchScreen` (`common/.../client/screen/WorkbenchScreen.java`)
- Extiende `Screen` de Minecraft con `renderBackground(GuiGraphics)` vacío (sin oscurecer el mundo 3D).
- Renderizado Diegético:
  - Marco esquemático del arma / herramienta.
  - Cajas de slots delimitadas en pantalla para:
    - Arma Principal (`SLOT_PRIMARY`)
    - Óptica / Mira (`SLOT_OPTIC`)
    - Cañón / Silenciador (`SLOT_BARREL`)
    - Empuñadura / Accesorio inferior (`SLOT_UNDERBARREL`)
  - Barra indicadora de Condición / Limpieza del arma con gradiente o tramos dinámicos:
    - Verde (> 70%)
    - Amarillo (> 30% a 70%)
    - Rojo (<= 30%)
  - Botones tácticos interactivos:
    - "Ciclar Cerrojo" -> reproduce `ModSounds.BOLT_CYCLE` y emite partículas visuales.
    - "Limpiar y Aceitar" -> incrementa la limpieza del arma/banco, reproduce `ModSounds.OIL_WIPE`.
    - "Ajustar Tornillos" -> reproduce `ModSounds.SCREW_TURN`.
    - "Salir [ESC]" -> cierra la pantalla y ejecuta `DiegeticCameraController.stop()`.
- Soporte para `mouseClicked`, `mouseDragged` y `keyPressed`.

### 2.3. `TacticalWorkbenchRenderer` (`common/.../client/render/TacticalWorkbenchRenderer.java`)
- Implementa `BlockEntityRenderer<TacticalWorkbenchBlockEntity>`.
- Renderiza el arma principal reposando horizontalmente sobre el tapete de trabajo.
- Renderiza accesorios montados (mira, silenciador, empuñadura) en posiciones relativas transformadas.
- Rota las transformaciones según el `FACING` de `TacticalWorkbenchBlock`.

### 2.4. Integración Fabric (`fabric/.../client/mixin/CameraMixin.java`)
- Inyección `@Inject(method = "setup", at = @At("TAIL"))` en `net.minecraft.client.Camera`.
- Aplica `DiegeticCameraController.applyCameraTransform(this, partialTicks)`.
- Registro de entrada de Fabric Client e inicialización de Mixin.

### 2.5. Integración Forge (`forge/.../client/ForgeClientEvents.java`)
- Suscripción a eventos de cámara (`ViewportEvent.ComputeCameraAngles`) en Forge EventBus.
- Integración en `TacticalWorkbenchForge` del cliente y registro del renderer.

## 3. Validación y Pruebas de Compilación
- Compilación puntual con `./gradlew :common:compileJava`.
- Compilación puntual con `./gradlew :fabric:compileJava`.
- Compilación puntual con `./gradlew :forge:compileJava`.
- Verificación de que no haya dependencias circulares ni fugas cliente/servidor en `common`.
