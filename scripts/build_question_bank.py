#!/usr/bin/env python3
import json, re
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
JAVAGUIDE = ROOT / "imported" / "JavaGuide" / "docs"
OUT = ROOT / "content" / "questions" / "autogen-v0.json"


INVALID_HEADING_PATTERNS = [
    r"^必看$",
    r"^🔥必看$",
    r"^\(必看.*\)$",
]


def normalize_heading(raw: str) -> str:
    h = raw
    h = re.sub(r"\[(.*?)\]\([^)]*\)", r"\1", h)  # markdown link -> text
    h = re.sub(r"[`*#]+", "", h)
    h = re.sub(r":[^:\s]+:", "", h)  # emoji shortcode, e.g. :+1:
    h = h.replace("🔥", "")
    h = re.sub(r"[（(]\s*必看[^）)]*[）)]", "", h)
    h = re.sub(r"\s+", " ", h).strip(" -—_：:·")
    return h.strip()


def is_invalid_heading(h: str) -> bool:
    if len(h) < 2:
        return True
    if any(re.fullmatch(p, h) for p in INVALID_HEADING_PATTERNS):
        return True
    return False


def stem_from_heading(h: str) -> str | None:
    cleaned = normalize_heading(h)
    if is_invalid_heading(cleaned):
        return None
    return f"关于『{cleaned}』，下列说法最准确的是？"


def category_from_path(path: Path) -> str:
    rel = path.relative_to(JAVAGUIDE)
    if len(rel.parts) > 1:
        return rel.parts[0]
    return "javaguide"


def slugify(text: str) -> str:
    s = text.lower()
    s = re.sub(r"[^a-z0-9\u4e00-\u9fff]+", "_", s)
    s = re.sub(r"_+", "_", s).strip("_")
    return s or "x"


def md_identity(path: Path) -> str:
    rel = path.relative_to(JAVAGUIDE)
    parent = "_".join(rel.parts[1:-1]) if len(rel.parts) > 2 else ""
    name = rel.stem
    raw = f"{parent}_{name}" if parent else name
    return slugify(raw)


def gen_from_md(path: Path, limit=None):
    text = path.read_text(encoding="utf-8", errors="ignore")
    heads = re.findall(r"^#{2,4}\s+(.+)$", text, flags=re.M)
    items = []
    cat = category_from_path(path)
    file_id = md_identity(path)
    selected = heads if limit is None else heads[:limit]
    idx = 0
    for h in selected:
        stem = stem_from_heading(h)
        if stem is None:
            continue
        idx += 1
        qid = f"{slugify(cat)}_{file_id}_{idx}"
        items.append({
            "id": qid,
            "type": "single",
            "category": cat,
            "subCategory": path.stem,
            "stem": stem,
            "options": ["选项A", "选项B", "选项C", "选项D"],
            "answers": ["选项A"],
            "explanation": "自动生成草稿题，需人工审核答案与解析。",
            "difficulty": 2,
            "tags": [cat, path.stem],
            "source": {"repo": "Snailclimb/JavaGuide", "path": str(path.relative_to(ROOT / "imported" / "JavaGuide"))}
        })
    return items


def main(max_questions: int | None = None):
    if not JAVAGUIDE.exists():
        raise SystemExit("JavaGuide docs not found. Put repo under imported/JavaGuide")
    all_items = []
    seen_paths = set()
    for md in sorted(JAVAGUIDE.rglob("*.md")):
        rel_lower = str(md.relative_to(JAVAGUIDE)).lower()
        if rel_lower in seen_paths:
            continue
        seen_paths.add(rel_lower)

        all_items.extend(gen_from_md(md, limit=None))
        if max_questions is not None and len(all_items) >= max_questions:
            break
    OUT.parent.mkdir(parents=True, exist_ok=True)
    OUT.write_text(json.dumps(all_items, ensure_ascii=False, indent=2), encoding="utf-8")
    print(f"generated {len(all_items)} questions -> {OUT}")


if __name__ == "__main__":
    import argparse
    ap = argparse.ArgumentParser()
    ap.add_argument("--max", type=int, default=None, help="max questions")
    args = ap.parse_args()
    main(args.max)
