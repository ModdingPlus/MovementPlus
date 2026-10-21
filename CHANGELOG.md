- Fix an incompatibility on Fabric with mods that also add player attributes (e.g. Tiered):
  the player's default attributes are now extended through Fabric API's attribute modification
  event instead of being replaced
- The Fabric jar now requires Fabric API 0.154.0 or newer
- Minecraft 26.3 is now supported as well; the same jars run on 26.2 and 26.3
- Notes for NeoForge + Minecraft 26.3 only:
  - movement_plus-server.toml is now movement_plus-synced.toml
    - files are compatible and only have to be renamed
    - this move is the result of a rename happening in NeoForge

