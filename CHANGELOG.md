# 2.1.0

- The config values for multi jumps and coyote time are now applied as
  attribute modifiers (defaults on `movement_plus:multi_jumps` and
  `movement_plus:coyote_time`), so equipment and other mods stack cleanly
  with the configured base.
- An api jar (events + attributes) is now attached to the Modrinth release
  and resolvable as `maven.modrinth:movement_plus:2.1.0+neoforge:api`.
- Minecraft version ranges are declared as 1.21.1-only.

# 2.0.0

Full rewrite for Minecraft 1.21.1 as a multiloader mod (NeoForge + Fabric) on Architectury.

## Added

- Fabric support (requires Fabric API, Architectury API, and Forge Config API Port)
- NeoForge support (requires Architectury API); LexForge is no longer supported
- Movement speed multiplier option
- Swim speed multiplier option and a `movement_plus:swim_speed` player attribute for equipment/mods to hook into
- In-game config screen (native on NeoForge; via Mod Menu on Fabric)
- German translations

## Changed

- All gameplay options are now multipliers applied as attribute modifiers, so they compose with other mods instead of overwriting attribute values; a multiplier of 1 leaves the attribute completely untouched
  - `stepHeight`/`stepHeightSneaking` are replaced by `stepHeightMultiplier`/`stepHeightSneakingMultiplier`
  - `jumpHeightBoost` (potion levels) is replaced by `jumpPowerMultiplier`; fall damage compensation now uses the vanilla safe fall distance attribute
- The custom `jump_height` attribute has been removed in favor of vanilla's `generic.jump_strength` (exists since 1.20.5); the jump power mixin is gone
- Server config values now sync to clients through a dedicated packet, keeping client-side jump prediction accurate on any server
- The mod API has been slimmed down to the midair jump events (`MidairJumpEvents`), now implemented as Architectury events; the input event API and attribute provider API have been removed
- Config file moved from `world/serverconfig/` to `config/` (NeoForge convention since 1.20.5)
