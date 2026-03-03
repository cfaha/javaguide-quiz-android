# 题库目录

- `seed-v1.json`：首批种子题（可直接导入）

## 规范
- 严格遵循根目录 `question.schema.json`
- 每题必须包含 `source.repo` 和 `source.path`
- `id` 全局唯一，推荐前缀：`<domain>_<编号>`

## 下一步扩充建议
1. 每个模块先扩到 100 题
2. 错题率高的模块优先扩充（并发/JVM/MySQL）
3. 统一做重复题检查和答案一致性校验
