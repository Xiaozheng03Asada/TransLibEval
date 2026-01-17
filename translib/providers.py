from __future__ import annotations

from pathlib import Path
from typing import Iterable, Optional

from openai import OpenAI

from .env import get_env, get_env_list, require_env

QWEN_DEFAULT_BASE = "https://dashscope.aliyuncs.com/compatible-mode/v1"
OPENAI_DEFAULT_BASE = "https://api.openai.com/v1"


def _infer_provider_name(provider: Optional[str], script_path: Optional[str]) -> str:
    if provider:
        return provider.lower()
    if script_path:
        name = Path(script_path).stem.lower()
        if name.startswith("sum_"):
            name = name[4:]
        return name
    return ""


def build_openai_client(
    provider: Optional[str] = None,
    *,
    script_path: Optional[str] = None,
) -> OpenAI:
    """Construct an OpenAI-compatible client configured via environment variables."""
    slug = _infer_provider_name(provider, script_path)
    if "qwen" in slug or "qwmax" in slug:
        api_key = require_env("QWEN_API_KEY")
        base_url = get_env("QWEN_API_BASE", QWEN_DEFAULT_BASE)
    else:
        api_key = require_env("OPENAI_API_KEY")
        base_url = get_env("OPENAI_BASE_URL", OPENAI_DEFAULT_BASE)
    return OpenAI(api_key=api_key, base_url=base_url)


def get_deepseek_headers(extra: Optional[dict[str, str]] = None) -> dict[str, str]:
    headers: dict[str, str] = {
        "Content-Type": "application/json",
        "Authorization": require_env("DEEPSEEK_AUTH_TOKEN"),
    }
    app_id = get_env("DEEPSEEK_APP_ID")
    if app_id:
        headers["appid"] = app_id
    if extra:
        headers.update(extra)
    return headers


def get_google_api_keys() -> list[str]:
    return get_env_list("GOOGLE_CSE_API_KEYS")


def get_google_cse_id() -> str:
    return require_env("GOOGLE_CSE_ID")


def ensure_non_empty(values: Iterable[str], *, what: str) -> list[str]:
    result = [value for value in values if value]
    if not result:
        raise RuntimeError(f"{what} 未设置。请在 .env 中填写所需值。")
    return result
