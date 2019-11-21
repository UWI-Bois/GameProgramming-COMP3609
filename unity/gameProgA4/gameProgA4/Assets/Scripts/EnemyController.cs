using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class EnemyController : MonoBehaviour
{

    public int speed;
    public int xMoveDir;
    private Rigidbody2D rb;
    private float hitDist = 1.0f;

    // Start is called before the first frame update
    void Start()
    {
        rb = GetComponent<Rigidbody2D>();
        speed = 2;
        xMoveDir = 1;
    }

    void Move()
    {
        // check the distance between enemy and the object its colliding with
        // if the distance is 0.7, do something
        RaycastHit2D hit = Physics2D.Raycast(
            transform.position, 
            new Vector2(
                xMoveDir, 0
            )
        );

        rb.velocity = new Vector2(
            xMoveDir, 0
        ) * speed;

        if (hit.distance < hitDist)
        {
            Flip();
            if(hit.collider.tag == "Player")
            {
                Destroy(hit.collider.gameObject);
            }
        } 
    }

    private void OnCollisionEnter2D(Collision2D collision)
    {
        // so using tilemaps, we can make new tilemaps and assign different tags to them, for ex: water and ground.
        //Debug.Log("enemy has collided with " + collision.collider.name + " with tag: " + collision.gameObject.tag);
        if (collision.gameObject.tag == "groundable");
    }

    void Flip()
    {
        if (xMoveDir > 0) xMoveDir = -1;
        else xMoveDir = 1;
    }

    // Update is called once per frame
    void Update()
    {
        Move();
    }
}
