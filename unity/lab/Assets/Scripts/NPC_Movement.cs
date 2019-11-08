using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class NPC_Movement : MonoBehaviour
{
    new Rigidbody2D rigidbody2D;
    Animator animator;

    public int direction = 1; // 1 = sumn, -1 = the opposite direction
    public float speed = 1.5f;
    public float timer;
    public float movementTime = 4.0f;

    // Start is called before the first frame update
    void Start()
    {
        rigidbody2D = GetComponent<Rigidbody2D>();
        timer = movementTime;
        animator = GetComponent<Animator>();
        //QualitySettings.vSyncCount = 0;
        //Application.targetFrameRate = 60;
    }

    // Update is called once per frame
    void Update()
    {
        Vector2 position = rigidbody2D.position;
        timer -= Time.deltaTime;

        if(timer < 0)
        {
            direction = -direction;
            timer = movementTime;
            animator.SetFloat("Move X", direction);
        }

        position.x = position.x + speed * direction * Time.deltaTime;
        rigidbody2D.MovePosition(position);
    }
}
