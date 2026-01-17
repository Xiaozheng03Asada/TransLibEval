from __future__ import annotations

import os
from pathlib import Path
from typing import Iterable, Optional

ROOT_DIR = Path(__file__).resolve().parents[1]
DEFAULT_ENV_PATH = ROOT_DIR / ".env"
_LOADED_FILES: set[Path] = set()


def _parse_env(lines: Iterable[str]) -> dict[str, str]:
    result: dict[str, str] = {}
    for raw_line in lines:
        line = raw_line.strip()
        if not line or line.startswith("#") or "=" not in line:
            continue
        key, value = line.split("=", 1)
        key = key.strip()
        value = value.strip().strip('"').strip("'")
        result[key] = value
    return result


def load_env_file(path: Optional[Path] = None, *, override: bool = False) -> None:
    """Load KEY=VALUE pairs from a .env file into ``os.environ``."""
    env_path = Path(path) if path else DEFAULT_ENV_PATH
    if not env_path.exists() or env_path in _LOADED_FILES:
        return
    data = _parse_env(env_path.read_text(encoding="utf-8").splitlines())
    for key, value in data.items():
        if override or key not in os.environ:
            os.environ[key] = value
    _LOADED_FILES.add(env_path)


def get_env(key: str, default: Optional[str] = None) -> Optional[str]:
    value = os.environ.get(key)
    if value is None or value == "":
        return default
    return value


def require_env(key: str) -> str:
    value = get_env(key)
    if value is None:
        raise RuntimeError(
            f"Environment variable '{key}' is required. "
            "Set it in .env (see .env.example) or export it in your shell."
        )
    return value


def get_env_list(key: str, *, sep: str = ",") -> list[str]:
    raw_value = get_env(key)
    if not raw_value:
        return []
    return [item.strip() for item in raw_value.split(sep) if item.strip()]
